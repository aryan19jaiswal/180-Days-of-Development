# D006 — Spring MVC architecture

**Build:** Trace one request through the whole chain with logs
**Done when:** You can draw the request lifecycle unaided
**Topics:** `DispatcherServlet` · handler mapping · request lifecycle · embedded Tomcat · filters vs interceptors

---

## 1. The problem

Servlet containers speak `HttpServletRequest` / `HttpServletResponse`. Without a framework you write one servlet per URL and hand-roll path parsing, JSON conversion, and error handling.

Spring MVC puts a **single front controller** (`DispatcherServlet`) in front of everything. It receives every request and decides which of your methods handles it.

## 2. The request lifecycle

```text
Client
  ↓  HTTP bytes
Embedded Tomcat (connector → worker thread http-nio-8080-exec-N)
  ↓  HttpServletRequest
Servlet Filter chain            container level, wraps everything
  ↓
DispatcherServlet               front controller
  ├─ HandlerMapping             "which handler for GET /orders?"
  ├─ Interceptor.preHandle
  ├─ HandlerAdapter             resolves args, invokes the method
  │     └─ OrderController.getOrders() → OrderService → OrderRepository
  ├─ Return value handler + HttpMessageConverter   (List<String> → JSON)
  ├─ Interceptor.postHandle
  ├─ Interceptor.afterCompletion
  ↓
Filter chain unwinds → Tomcat writes response → Client
```

## 3. Key learnings

### Tomcat and threads
- `SpringApplication.run()` creates and starts the embedded Tomcat during context refresh (`ServletWebServerApplicationContext`, `TomcatServletWebServerFactory`) and registers `DispatcherServlet` with it.
- Requests run on a **Tomcat worker thread** (`http-nio-8080-exec-N`), never on `main`. `main` returns after startup.
- Blocking inside a controller occupies one worker thread (default pool is about 200). This is the reason WebFlux and virtual threads exist.

### Handler mapping and adapter
- `RequestMappingHandlerMapping` maps `GET /orders` to `OrderController#getOrders()`. Log: `Mapped to ...OrderController#getOrders()`.
- `HandlerAdapter` invokes the method and resolves its arguments.

### JSON conversion
- `@RestController` = `@Controller` + `@ResponseBody`: the return value is written to the body, not treated as a view name.
- `RequestResponseBodyMethodProcessor` asks the `HttpMessageConverter`s to write the value. `MappingJackson2HttpMessageConverter` wins for `List<String>`.
- Content negotiation: `Accept: */*` → `application/json`. Log: `Using 'application/json', given [*/*]`.

### Filters vs interceptors

| | Filter | Interceptor |
|---|---|---|
| Belongs to | Servlet container | Spring MVC |
| Runs around | The whole `DispatcherServlet` call | Handler invocation |
| Sees requests with no handler | Yes | Only if a handler (even a static-resource one) was mapped |
| Knows the handler | No | Yes (`handler` argument) |
| Registered via | `@Component` / `FilterRegistrationBean` | `WebMvcConfigurer.addInterceptors` |

Order is always: `FILTER before` → `preHandle` → controller → `postHandle` → `afterCompletion` → `FILTER after`.

`OncePerRequestFilter` guarantees one execution per request. It skips error dispatches by default.

## 4. What the logs proved

### `GET /orders` → 200 (all on one `exec` thread)

```text
FILTER before  GET /orders
DispatcherServlet - GET "/orders"
Mapped to OrderController#getOrders()
INTERCEPTOR preHandle
CONTROLLER getOrders
SERVICE listOrders
Using 'application/json', given [*/*]
INTERCEPTOR postHandle
INTERCEPTOR afterCompletion ex=null
FILTER after status=200
```

### `GET /nope` → 404
- The filter ran, as expected.
- The **interceptor also ran**. Spring Boot maps a catch-all `ResourceHttpRequestHandler` on `/**`, so a handler is always found. It looked for a static file called `nope`, found none, and set 404.
- Tomcat then made a second **`/error` dispatch** to `BasicErrorController`, so the interceptor ran again. The filter did not, because `OncePerRequestFilter` skips error dispatches.

### Controller throws `RuntimeException("boom")` → 500

| Hook | Result |
|---|---|
| `preHandle` | ran |
| `postHandle` | **skipped** |
| `afterCompletion` | **ran, `ex=java.lang.RuntimeException: boom`** |
| `FILTER after` | ran (in `finally`), but saw **`status=200`** |
| `/error` dispatch | yes, via `BasicErrorController`, final status 500 |

## 5. Rules of thumb

- `postHandle` runs only on success. Never put cleanup there.
- **`afterCompletion` is the cleanup hook.** It runs whenever `preHandle` returned true and receives the exception. Use it for `ThreadLocal.remove()`, metrics, and releasing resources.
- A filter cannot reliably log the final status of a failed request. The 500 is set after the exception leaves the filter chain. Handle exceptions inside the app (`@ControllerAdvice`, coming in D007).
- An unhandled exception is wrapped in a `ServletException` ("Request processing failed") and rethrown to Tomcat, which triggers the `/error` dispatch.
- Use a **filter** for cross-cutting concerns that don't need Spring MVC (request IDs, security, CORS, encoding). Use an **interceptor** when you need the handler or want to act per mapped endpoint.

## 6. Production notes

- The default 404/500 JSON body includes a full stack trace in the `trace` field. That is fine locally and a leak in prod. Turn it off in `application-prod.properties`.
- `DispatcherServlet` DEBUG and `method.annotation` TRACE logging is very noisy. Keep it in `application-local.properties`, not in the shared file.
- The startup thread is `restartedMain` because DevTools restarts the app in its own classloader thread.

## 7. What was built

- `Config/RequestTraceFilter` — `OncePerRequestFilter` logging before/after in a `finally`.
- `Config/RequestTraceInterceptor` — logs `preHandle`, `postHandle`, `afterCompletion`.
- `Config/WebConfig` — `WebMvcConfigurer` registering the interceptor.
- Log lines in `OrderController` and `OrderService`.
- `application.properties` — thread-aware log pattern plus `DispatcherServlet` DEBUG and `method.annotation` TRACE.

## 8. Interview drill

1. **Definition:** What is `DispatcherServlet` and what does it do?
2. **Why:** Why does Spring MVC use a front controller?
3. **How:** How does `List<String>` become JSON with no JSON code in the controller?
4. **Trade-off:** Filter or interceptor for request-ID logging? What changes if you need the handler method?
5. **Scenario:** A controller throws. Which of `postHandle`, `afterCompletion` and `FILTER after` run, and what does `ex` contain?
6. **Scenario:** A request to an unmapped URL still triggers your interceptor. Why?

## 9. Checkpoint

- **Explain:** Draw the lifecycle from `Client` to `Client` without looking.
- **Apply:** Add a request-ID filter that puts an ID in the MDC and clears it in `finally`.
- **Diagnose:** An interceptor leaks a `ThreadLocal` on failed requests. Where was the cleanup wrongly placed?
- **Interview:** Explain the difference between a filter and an interceptor in 30 seconds.
