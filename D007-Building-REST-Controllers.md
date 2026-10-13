# D007 — Building REST controllers

**Build:** CRUD API for the order domain
**Done when:** All endpoints work in Postman/HTTPie
**Topics:** `@RestController` · mappings · `@PathVariable`/`@RequestParam`/`@RequestBody` · status codes · content negotiation

---

## 1. The problem

A REST API is a contract: **URL + HTTP method + status code + body**. The controller translates between that contract and service calls. Most REST bugs are contract bugs, not Java bugs: `200` on a create, `200` with an empty body for a missing resource, a `GET` that mutates state.

## 2. The CRUD contract

| Operation | Request | Success | Failure |
|---|---|---|---|
| List | `GET /orders[?status=SHIPPED]` | 200 + array | 400 (bad `status`) |
| Read | `GET /orders/{id}` | 200 + order | 404, 400 (non-numeric id) |
| Create | `POST /orders` + JSON | **201** + `Location` + order | 400, 415 |
| Replace | `PUT /orders/{id}` + JSON | 200 + order | 404, 400, 415 |
| Delete | `DELETE /orders/{id}` | **204**, no body | 404 |

## 3. Where each argument comes from

```text
POST /orders/42?notify=true
Content-Type: application/json
{"item":"Batarangs"}

@PathVariable Long id        ← 42       URL path template {id}
@RequestParam boolean notify ← true     query string
@RequestBody  Order body     ← {...}    HttpMessageConverter (Jackson), read direction
```

- All three are resolved by `HandlerMethodArgumentResolver`s inside `RequestMappingHandlerAdapter`, **before** your method runs.
- `@RequestBody` uses `RequestResponseBodyMethodProcessor`, the same class that writes return values (D006). It picks a converter by `Content-Type` on the way in and by `Accept` on the way out.
- `@RequestParam` is required by default. `required = false` makes it `null` when absent. This is how `?status=` stays optional.
- `@RequestMapping("/orders")` on the class plus `@GetMapping("/{id}")` on the method gives `/orders/{id}`. `@GetMapping` without a path means "the class path itself".

## 4. Status codes that matter

| Code | When | Who produces it here |
|---|---|---|
| 200 | Read/replace OK | default |
| 201 | Resource created, plus `Location` | `ResponseEntity.created(uri)` |
| 204 | Success, nothing to return | `ResponseEntity.noContent()` |
| 400 | Unparseable input: bad JSON, empty body, bad enum, non-numeric id | Spring exception resolvers |
| 404 | Resource does not exist | `ResponseStatusException` |
| 405 | URL exists but not for this method (e.g. `DELETE /orders`) | `HttpRequestMethodNotSupportedException` |
| 406 | `Accept` asks for something no converter can produce | `HttpMediaTypeNotAcceptableException` |
| 415 | `Content-Type` of the body is not readable | `HttpMediaTypeNotSupportedException` |

`4xx` means the client should change the request. `5xx` means the server failed. A missing order is the client's problem (404), not a 500.

## 5. Content negotiation

- `Content-Type` says what the client **sends**. A mismatch gives **415**.
- `Accept` says what the client **wants back**. A mismatch gives **406**.
- Only Jackson is on the classpath, so JSON is the only representation. Add `jackson-dataformat-xml` and the same controller would start serving XML. The controller code does not change.
- Mnemonic: *Content-Type = my body, Accept = your body.*

## 6. Design decisions and why

**Not-found as an exception (`ResponseStatusException`) rather than `ResponseEntity.notFound()`**
Keeps the happy path returning a plain `Order`. Every missing-resource case goes through `orElseThrow`, and it can later be centralized with `@RestControllerAdvice`. The cost is that the status is not visible in the method signature.

**`ResponseEntity` only where headers or a non-default status are needed** (create, delete). A plain return type is clearer everywhere else.

**Server-assigned IDs.** `POST` ignores any `id` in the body. A client must not choose primary keys. Test: `createReturns201WithLocationAndIgnoresClientId`.

**`PUT` is replace, not upsert.** `PUT` on a missing id returns 404 and does not create. Test: `putMissingIs404AndDoesNotCreate`.

**`Location` is built from the current request** (`ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")`) so the path isn't hardcoded and still works behind a different context path.

**Thread-safe store.** Tomcat serves requests on many worker threads (`http-nio-8080-exec-N`), so a plain `HashMap` and `long counter++` would be data races. The repository uses `ConcurrentSkipListMap` (thread-safe and sorted by id) plus `AtomicLong`.
`replace` uses `computeIfPresent`, which is atomic. A check-then-act sequence (`if (containsKey) put`) could resurrect an order that a concurrent `DELETE` just removed.

**`record Order(Long id, String item, OrderStatus status)`**: immutable, no boilerplate, serialized by Jackson out of the box. `OrderStatus` is an enum, so an invalid value fails at deserialization (400) instead of storing garbage.

## 7. Known gaps (intentional, fixed in D008)

- The `Order` entity-like record is used directly as the request and response body. D008's rule is *"no entity ever crosses the controller boundary"*, and this is the thing that gets fixed.
- No validation: `{}` creates an order with `item = null`. D008 adds Bean Validation.
- Error responses are Spring Boot's default JSON, and with DevTools they include a full **stack trace** (`trace`). That is a leak in prod. Proper error bodies (`ProblemDetail`) come later.
- Data is in memory and lost on restart. Persistence comes with JPA.

## 8. Observations from running it

```text
POST   /orders {"item":"Cowl"}   → 201, Location: http://localhost:8080/orders/4
GET    /orders/4                 → 200
GET    /orders?status=SHIPPED    → 200, only SHIPPED
PUT    /orders/4                 → 200
DELETE /orders/4                 → 204 (no body)
DELETE /orders/4  (again)        → 404
GET    /orders/abc               → 400  (MethodArgumentTypeMismatchException; controller never ran)
GET    /orders  Accept: xml      → 406  (empty body)
POST   /orders  text/plain       → 415
```

- `DELETE` is idempotent in effect (the server state is the same after one or many calls), though the second response is 404 rather than 204. That is an accepted convention.
- `POST` is not idempotent: sending it twice creates two orders. `PUT` and `DELETE` are idempotent. `GET` is safe and idempotent.
- The 406 response has an empty body, whereas 400/404/415 go through Boot's `/error` dispatch and carry a JSON body. The reason is that for 406 the error body itself cannot be rendered in an acceptable type.

## 9. Verification

Automated (all green: 16 controller, 4 repository, 4 service tests):

- `OrderControllerTest`: `@SpringBootTest` + `MockMvc`, full context with real converters, so 406/415 are exercised for real. Tests create their own data, so they are order-independent.
- `OrderRepositoryTest`: includes a **concurrency test** (16 threads x 200 inserts must produce 3200 unique ids).
- `OrderServiceTest`: plain unit test, no Spring (D003 style).

```bash
./gradlew test
```

Manual (HTTPie / curl against `./gradlew bootRun`):

```bash
http POST :8080/orders item=Cowl
http GET :8080/orders/4
http GET ':8080/orders?status=SHIPPED'
http PUT :8080/orders/4 item="Cowl v2" status=SHIPPED
http DELETE :8080/orders/4
http GET :8080/orders/abc
http GET :8080/orders Accept:application/xml
http POST :8080/orders Content-Type:text/plain <<< x
```

Spring Boot 4 note: `AutoConfigureMockMvc` lives in `org.springframework.boot.webmvc.test.autoconfigure` (it moved out of `spring-boot-test-autoconfigure` into the new `spring-boot-webmvc-test` module).

## 10. What was built

- `Orders/Order` (record) and `Orders/OrderStatus` (enum).
- `Orders/OrderRepository`: thread-safe in-memory store, seeded with the original 3 orders.
- `Orders/OrderService`: list with optional status filter, find, create (default status `CREATED`), update, delete.
- `Orders/OrderController`: 5 endpoints under `@RequestMapping("/orders")`.
- Tests: `OrderControllerTest`, `OrderRepositoryTest`, and an updated `OrderServiceTest`.

## 11. Interview drill (with answers)

**1. What does `@RestController` do?**
It is `@Controller` + `@ResponseBody`. Return values are written to the response body through an `HttpMessageConverter` instead of being resolved as a view name. It does not change how requests are routed.

**2. `@PathVariable` vs `@RequestParam` vs `@RequestBody`?**
`@PathVariable` binds a URL path segment (`/orders/{id}`) and identifies a resource. `@RequestParam` binds a query-string (or form) parameter and is for filtering, sorting and paging. `@RequestBody` deserializes the request body with a converter chosen by `Content-Type`. Only one `@RequestBody` per method, because the body stream is read once.

**3. A client sends `GET /orders/abc` to `/orders/{id}` with `Long id`. What happens?**
The mapping matches, because the template accepts any string. Argument resolution then fails to convert `"abc"` and throws `MethodArgumentTypeMismatchException`, which resolves to **400**. The controller method never runs.

**4. A method returns `null` for a missing resource. What does the client see?**
`200 OK` with an empty body. That is a contract bug. Model absence explicitly: `Optional` plus `orElseThrow` to a 404, or `ResponseEntity.notFound()`.

**5. Which status for a successful `POST` that creates something, and what else belongs in the response?**
`201 Created` with a `Location` header pointing at the new resource, and usually the created representation in the body. `200` loses the "created" information. `204` is for responses with no body.

**6. 400 vs 404 vs 405 vs 406 vs 415: when does each occur?**
400: the request is malformed (bad JSON, bad type, missing body). 404: the resource or URL doesn't exist. 405: the URL exists but not for this HTTP method. 406: the server cannot produce what `Accept` asks for. 415: the server cannot read the `Content-Type` of the body.

**7. How does content negotiation work in Spring MVC?**
For input, the `Content-Type` selects a converter that `canRead` the type. For output, the `Accept` header (or a configured strategy) is matched against the media types producible by converters that `canWrite` the return type. The best match is chosen. No match gives 406 (output) or 415 (input).

**8. Which HTTP methods are safe and which are idempotent? Why does it matter?**
Safe (no state change): `GET`, `HEAD`, `OPTIONS`. Idempotent (same effect when repeated): `GET`, `PUT`, `DELETE` (plus the safe ones). `POST` is neither. It matters for retries: clients, proxies and gateways can retry idempotent calls on timeout, while a retried `POST` may create duplicates unless you add an idempotency key.

**9. `PUT` vs `PATCH`?**
`PUT` replaces the whole resource with the representation sent. Omitted fields are reset. `PATCH` applies a partial change. Both are covered by the same mapping style, but `PATCH` needs null-vs-absent handling.

**10. Why are the controller's in-memory structures `ConcurrentSkipListMap`/`AtomicLong` and not `HashMap`/`long`?**
The controller is a singleton shared by all Tomcat worker threads. A `HashMap` can lose entries or corrupt under concurrent writes, and `counter++` is a read-modify-write that can hand out duplicate ids. The concurrency test (16 threads x 200 inserts yielding 3200 unique ids) proves the ids don't collide.

**11. Why should the server, not the client, assign the ID?**
Clients can collide with each other or overwrite someone else's record. The server owns the identity space. The body `id` is ignored on create, and `PUT` takes the id from the URL.

**12. Why `ResponseStatusException` instead of returning `ResponseEntity.notFound()` everywhere?**
It keeps controller methods returning the domain type and keeps not-found handling to a single `orElseThrow`. It also leaves room to centralize error shape later with `@RestControllerAdvice`. The trade-off is that the status isn't visible in the signature, and throwing is a little heavier than returning.

**13. Scenario: `PUT /orders/5` arrives right as another request deletes order 5. How do you avoid "resurrecting" the order?**
Make the update atomic against existence: `computeIfPresent` in the map (or `UPDATE ... WHERE id = ?` checking the affected row count in SQL). A check-then-act (`if (exists) put`) is a race.

**14. Scenario: your API returns a stack trace in a 500 response. What is wrong and how do you fix it?**
Internal details are leaking (classes, paths, library versions). Disable `server.error.include-stacktrace` outside local profiles, and return a stable, structured error body (`ProblemDetail`) from a `@RestControllerAdvice`. Keep the full trace in the logs only.

**15. Why must you not expose the persistence entity directly as the API body?**
It couples the public contract to the storage schema, allows over-posting (clients setting `id`, `status`, etc.), and leaks fields. This is today's known gap, and D008's rule is that no entity ever crosses the controller boundary.

## 12. Checkpoint

- **Explain:** For each of the 5 endpoints, say the status code on success and on failure, and who produces it.
- **Apply:** Add `PATCH /orders/{id}/status` that changes only the status. What status code and what happens on an invalid transition?
- **Diagnose:** `POST /orders` returns 415 even though the body looks like JSON. What do you check first? (`Content-Type` header: `curl -d` defaults to `application/x-www-form-urlencoded`.)
- **Interview:** Explain why `POST` is not idempotent and how you would make order creation safe to retry.
