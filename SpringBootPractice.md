# Development

*Spring Boot → Kafka & Redis → Spring AI → Langchain4j*

This is the weekday track. Monday to Friday, two focused hours at a time, I build production-grade Spring Boot backends with Kafka, Redis and the rest of the industry toolkit, then add real AI to them with Spring AI and Langchain4j, and finish by turning all of it into interview-ready answers. 130 build-days. No filler days, no passive watching - every day ends with something that compiles and runs.

**0 of 130 done**

---

## Phase 1 - Spring Boot & Industry Practice

*85 days*

> Build backends the way a product company builds them - secured, tested, observable, event-driven, deployable.

### Week 01 · Spring Core & Boot Foundations

#### Mon 5 Oct · D001 - The Spring container

- [ ] Done

*Week 01*

**Build:** Scaffold the practice service and wire a service layer through the container

**Done when:** Zero new for services; you can explain what the container builds at startup

**Topics:** IoC · ApplicationContext vs BeanFactory · what a "bean" really is · Spring module map

**Notes:**

#### Tue 6 Oct · D002 - Dependency injection

- [ ] Done

*Week 01*

**Build:** Refactor to constructor injection everywhere

**Done when:** No @Autowired on fields

**Topics:** Constructor vs field vs setter · @Component/@Bean/@Configuration · stereotypes · component scan · @Qualifier/@Primary

**Notes:**

#### Wed 7 Oct · D003 - Bean lifecycle & scopes

- [ ] Done

*Week 01*

**Build:** Custom BeanPostProcessor that times bean init

**Done when:** You can narrate the full lifecycle from memory

**Topics:** Singleton/prototype/request/session · @PostConstruct/@PreDestroy · BeanPostProcessor · circular dependency fixes

**Notes:**

#### Thu 8 Oct · D004 - Spring Boot, demystified

- [ ] Done

*Week 01*

**Build:** Write your own tiny auto-configuration starter

**Done when:** Your starter auto-configures in a fresh app

**Topics:** Starters · @SpringBootApplication · auto-configuration · AutoConfiguration.imports · conditions · --debug report

**Notes:**

#### Fri 9 Oct · D005 - Configuration & profiles

- [ ] Done

*Week 01*

**Build:** Multi-profile config (local/dev/prod)

**Done when:** App boots correctly under all three profiles

**Topics:** application.yml · property precedence · @ConfigurationProperties + validation · profiles · env vars · @Value

**Notes:**

### Week 02 · Web Layer & API Design

#### Mon 12 Oct · D006 - Spring MVC architecture

- [ ] Done

*Week 02*

**Build:** Trace one request through the whole chain with logs

**Done when:** You can draw the request lifecycle unaided

**Topics:** DispatcherServlet · handler mapping · request lifecycle · embedded Tomcat · filters vs interceptors

**Notes:**

#### Tue 13 Oct · D007 - Building REST controllers

- [ ] Done

*Week 02*

**Build:** CRUD API for the order domain

**Done when:** All endpoints work in Postman/HTTPie

**Topics:** @RestController · mappings · @PathVariable/@RequestParam/@RequestBody · status codes · content negotiation

**Notes:**

#### Wed 14 Oct · D008 - DTOs, mapping & validation

- [ ] Done

*Week 02*

**Build:** Full DTO layer + validation with clean 400 responses

**Done when:** No entity ever crosses the controller boundary

**Topics:** Entity-DTO separation · MapStruct · Jakarta Bean Validation · custom validators · validation groups

**Notes:**

#### Thu 15 Oct · D009 - Error handling contract

- [ ] Done

*Week 02*

**Build:** One global error handler, consistent error body

**Done when:** Every failure path returns the same JSON shape

**Topics:** @ControllerAdvice · @ExceptionHandler · RFC-7807 ProblemDetail · error codes

**Notes:**

#### Fri 16 Oct · D010 - API design discipline

- [ ] Done

*Week 02*

**Build:** Paged + filterable endpoints with a published OpenAPI spec

**Done when:** Swagger UI documents the whole API accurately

**Topics:** Richardson maturity · versioning · pagination/sorting/filtering · idempotency · OpenAPI

**Notes:**

### Week 03 · Persistence with JPA

#### Mon 19 Oct · D011 - Spring Data JPA basics

- [ ] Done

*Week 03*

**Build:** Repositories on real Postgres

**Done when:** All CRUD passes against real Postgres

**Topics:** JPA vs Hibernate vs Spring Data · entities · ID generation · JpaRepository · Postgres via Docker

**Notes:**

#### Tue 20 Oct · D012 - Relationship mapping

- [ ] Done

*Week 03*

**Build:** Model Order > OrderItem > Product properly

**Done when:** Bidirectional relations stay consistent both ways

**Topics:** @OneToMany/@ManyToOne/@ManyToMany · owning side · cascade · orphanRemoval · LAZY vs EAGER

**Notes:**

#### Wed 21 Oct · D013 - Querying

- [ ] Done

*Week 03*

**Build:** Dynamic multi-filter search with Specification

**Done when:** One endpoint handles 5 optional filters

**Topics:** Derived queries · JPQL · @Query · native queries · projections · Specification

**Notes:**

#### Thu 22 Oct · D014 - Transactions

- [ ] Done

*Week 03*

**Build:** Money-transfer-style service with correct rollback

**Done when:** Failure mid-way leaves zero partial state

**Topics:** @Transactional · propagation · isolation levels · rollback rules · self-invocation trap · read-only

**Notes:**

#### Fri 23 Oct · D015 - JPA performance

- [ ] Done

*Week 03*

**Build:** Find and kill every N+1 with SQL logging on

**Done when:** Query count per endpoint is asserted in a test

**Topics:** N+1 detection · JOIN FETCH · entity graphs · batch inserts · first/second-level cache · HikariCP tuning

**Notes:**

### Week 04 · Data Layer & Redis

#### Mon 26 Oct · D016 - Schema migrations

- [ ] Done

*Week 04*

**Build:** Move the whole schema into Flyway migrations

**Done when:** Fresh DB builds itself from V1 upward

**Topics:** Flyway (or Liquibase) · versioned/repeatable migrations · rollback strategy · ddl-auto: validate

**Notes:**

#### Tue 27 Oct · D017 - Auditing & locking

- [ ] Done

*Week 04*

**Build:** Auditing + optimistic locking on Order

**Done when:** Concurrent update test fails loudly, not silently

**Topics:** @CreatedDate/@LastModifiedBy · Envers · soft deletes · optimistic (@Version) vs pessimistic locking

**Notes:**

#### Wed 28 Oct · D018 - Redis & Spring Cache

- [ ] Done

*Week 04*

**Build:** Cache the product catalogue in Redis

**Done when:** Cache hit ratio visible, invalidation correct

**Topics:** Redis basics · Spring Cache abstraction · @Cacheable/@CacheEvict · TTL · serialisation · cache stampede

**Notes:**

#### Thu 29 Oct · D019 - Redis patterns

- [ ] Done

*Week 04*

**Build:** Redis-backed rate limiter + a sorted-set leaderboard

**Done when:** Limiter holds under concurrent calls

**Topics:** Strings/hashes/sets/sorted sets · sessions · distributed locks · rate limiting · pub/sub · Streams

**Notes:**

#### Fri 30 Oct · D020 - MongoDB & polyglot persistence

- [ ] Done

*Week 04*

**Build:** Store audit/event logs in MongoDB

**Done when:** Two datasources coexist without conflict

**Topics:** Document modelling · Spring Data MongoDB · when NoSQL wins · multi-datasource config · Testcontainers

**Notes:**

### Week 05 · Testing Like a Professional

#### Mon 2 Nov · D021 - Unit testing services

- [ ] Done

*Week 05*

**Build:** Full unit-test suite for the service layer

**Done when:** Services tested with zero Spring context

**Topics:** JUnit 5 lifecycle · parameterised tests · Mockito (stub/verify/captors) · AssertJ · test naming

**Notes:**

#### Tue 3 Nov · D022 - Slice tests

- [ ] Done

*Week 05*

**Build:** Controller and repository slice tests

**Done when:** Slice suite runs in seconds, not minutes

**Topics:** @WebMvcTest + MockMvc · @DataJpaTest · @JsonTest · @MockitoBean · slices vs full context

**Notes:**

#### Wed 4 Nov · D023 - Integration testing

- [ ] Done

*Week 05*

**Build:** End-to-end test against real containers

**Done when:** Full journey test green on a clean machine

**Topics:** @SpringBootTest · Testcontainers (Postgres/Redis/Kafka) · test data builders · @Sql

**Notes:**

#### Thu 5 Nov · D024 - Testing external dependencies

- [ ] Done

*Week 05*

**Build:** Stub a third-party API with WireMock

**Done when:** No test touches the real internet

**Topics:** WireMock · contract testing concepts · flaky-test hygiene · clock/randomness injection

**Notes:**

#### Fri 6 Nov · D025 - Test strategy & CI

- [ ] Done

*Week 05*

**Build:** Wire tests + coverage gate into GitHub Actions

**Done when:** PR fails if coverage or tests fail

**Topics:** Test pyramid · JaCoCo coverage gates · mutation testing (PIT) · parallel execution · GitHub Actions test stage

**Notes:**

### Week 06 · Spring Security I

#### Mon 9 Nov · D026 - Security architecture

- [ ] Done

*Week 06*

**Build:** Lock down the API with a minimal config

**Done when:** Unauthenticated calls get 401, not 500

**Topics:** Filter chain internals · SecurityFilterChain DSL · SecurityContextHolder · authentication vs authorization

**Notes:**

#### Tue 10 Nov · D027 - Authentication

- [ ] Done

*Week 06*

**Build:** DB-backed user store with registration + login

**Done when:** Passwords hashed, never logged

**Topics:** UserDetailsService · AuthenticationProvider · PasswordEncoder (BCrypt/Argon2) · stateless vs session

**Notes:**

#### Wed 11 Nov · D028 - JWT authentication

- [ ] Done

*Week 06*

**Build:** Full JWT login/refresh/logout flow

**Done when:** Expired/tampered tokens rejected correctly

**Topics:** Token structure · signing · access vs refresh tokens · custom auth filter · rotation & revocation (Redis blacklist)

**Notes:**

#### Thu 12 Nov · D029 - Authorization

- [ ] Done

*Week 06*

**Build:** Role-based + ownership-based access rules

**Done when:** User A can never touch User B's data

**Topics:** Roles vs authorities · URL rules · method security (@PreAuthorize, @PostFilter) · ownership checks

**Notes:**

#### Fri 13 Nov · D030 - OAuth2 & OIDC

- [ ] Done

*Week 06*

**Build:** Add social login + resource-server validation

**Done when:** Login via provider issues a valid app session

**Topics:** Grant types · resource server · client registration · Google/GitHub login · JWK/JWT validation · Keycloak overview

**Notes:**

### Week 07 · Spring Security II & Hardening

#### Mon 16 Nov · D031 - Web hardening

- [ ] Done

*Week 07*

**Build:** Correct CORS + header policy for a real SPA client

**Done when:** Browser client works with no wildcard CORS

**Topics:** CORS done properly · CSRF (when it matters) · security headers · HTTPS/HSTS · cookie flags

**Notes:**

#### Tue 17 Nov · D032 - Abuse protection

- [ ] Done

*Week 07*

**Build:** Per-user + per-IP rate limiting

**Done when:** 429s returned with Retry-After

**Topics:** Rate limiting (Bucket4j/Resilience4j/Redis) · login throttling · account lockout · audit trail

**Notes:**

#### Wed 18 Nov · D033 - OWASP Top 10 for Spring

- [ ] Done

*Week 07*

**Build:** Threat-model your own API and fix 3 real gaps

**Done when:** Dependency scan clean of high CVEs

**Topics:** Injection · broken access control · SSRF · insecure deserialization · mass assignment · dependency CVEs

**Notes:**

#### Thu 19 Nov · D034 - Access-control modelling

- [ ] Done

*Week 07*

**Build:** Add tenant isolation to the data layer

**Done when:** Cross-tenant read is impossible by construction

**Topics:** RBAC vs ABAC · permission model design · multi-tenancy (row/schema/db) · tenant resolution

**Notes:**

#### Fri 20 Nov · D035 - Secrets & security testing

- [ ] Done

*Week 07*

**Build:** Move all secrets out of the repo + write authz tests

**Done when:** Repo has zero secrets, authz tests pass

**Topics:** Vault/AWS Secrets Manager · config encryption · key rotation · security-focused tests

**Notes:**

### Week 08 · Async, Scheduling & Batch

#### Mon 23 Nov · D036 - Application events

- [ ] Done

*Week 08*

**Build:** Emit domain events on order state changes

**Done when:** Side effects removed from the core service

**Topics:** ApplicationEventPublisher · @EventListener · @TransactionalEventListener · sync vs async listeners

**Notes:**

#### Tue 24 Nov · D037 - Async execution

- [ ] Done

*Week 08*

**Build:** Async email/notification dispatch

**Done when:** Request latency unaffected by slow side work

**Topics:** @Async · TaskExecutor config · pool sizing · exception handling in async · CompletableFuture in Spring

**Notes:**

#### Wed 25 Nov · D038 - Scheduling

- [ ] Done

*Week 08*

**Build:** Nightly report job, safe across 2 instances

**Done when:** Job runs exactly once with 2 instances up

**Topics:** @Scheduled · cron expressions · fixed rate vs delay · TaskScheduler · ShedLock for multi-instance

**Notes:**

#### Thu 26 Nov · D039 - Spring Batch I

- [ ] Done

*Week 08*

**Build:** Batch import of a large CSV into the DB

**Done when:** 1M rows imported with restart-on-failure

**Topics:** Job/Step/Chunk model · readers · processors · writers · job repository · restartability

**Notes:**

#### Fri 27 Nov · D040 - Spring Batch II

- [ ] Done

*Week 08*

**Build:** Partition the import job, measure the speedup

**Done when:** Throughput improves and failures are skippable

**Topics:** Partitioning · parallel steps · listeners · skip/retry policies · batch metrics

**Notes:**

### Week 09 · Kafka I

#### Mon 30 Nov · D041 - Messaging fundamentals

- [ ] Done

*Week 09*

**Build:** Design note: which order flows should be async and why

**Done when:** Decision documented with trade-offs

**Topics:** Queues vs topics · pub/sub · delivery semantics · ordering · backpressure · when to go async

**Notes:**

#### Tue 1 Dec · D042 - Kafka fundamentals

- [ ] Done

*Week 09*

**Build:** Run Kafka locally and produce/consume with the CLI tools

**Done when:** You can explain partition, offset and consumer group aloud

**Topics:** Brokers · topics · partitions · replication · offsets · KRaft · local Kafka via Compose

**Notes:**

#### Wed 2 Dec · D043 - Producers with Spring Kafka

- [ ] Done

*Week 09*

**Build:** Publish order events keyed by order id

**Done when:** Events for one order land in one partition, in order

**Topics:** KafkaTemplate · JSON/Avro serialisation · keys & partitioning · acks · idempotent producer · batching/linger

**Notes:**

#### Thu 3 Dec · D044 - Consumers

- [ ] Done

*Week 09*

**Build:** Consume order events in a group of 3

**Done when:** Scaling consumers splits partitions with no duplicates

**Topics:** @KafkaListener · consumer groups · offsets & commit strategies · rebalancing · concurrency

**Notes:**

#### Fri 4 Dec · D045 - Failure handling

- [ ] Done

*Week 09*

**Build:** Add retry topic + dead-letter topic handling

**Done when:** A poison message can't stall the consumer

**Topics:** Retries with backoff · DefaultErrorHandler · dead-letter topics · poison messages · non-blocking retry topics

**Notes:**

### Week 10 · Kafka II & Messaging Reliability

#### Mon 7 Dec · D046 - Idempotent consumers & transactions

- [ ] Done

*Week 10*

**Build:** Make the order consumer idempotent

**Done when:** Replaying the topic creates no duplicates

**Topics:** At-least-once vs exactly-once · idempotency keys · Kafka transactions · read-process-write

**Notes:**

#### Tue 8 Dec · D047 - Transactional outbox

- [ ] Done

*Week 10*

**Build:** Implement the outbox pattern for order events

**Done when:** No event lost when the DB commits and the broker fails

**Topics:** Dual-write problem · outbox table · polling publisher vs CDC (Debezium overview)

**Notes:**

#### Wed 9 Dec · D048 - Schema evolution

- [ ] Done

*Week 10*

**Build:** Evolve an event schema without breaking consumers

**Done when:** An old consumer reads the new event

**Topics:** JSON vs Avro/Protobuf · Schema Registry · compatibility modes · event versioning

**Notes:**

#### Thu 10 Dec · D049 - Kafka Streams & Connect

- [ ] Done

*Week 10*

**Build:** Per-minute order aggregation with Kafka Streams

**Done when:** Live count matches the source data

**Topics:** Stateless/stateful ops · KTable · windowing · Kafka Connect overview · when Streams beats a consumer

**Notes:**

#### Fri 11 Dec · D050 - Kafka ops, testing & RabbitMQ

- [ ] Done

*Week 10*

**Build:** Test the flow with Testcontainers; run one flow on RabbitMQ

**Done when:** You can justify choosing one over the other

**Topics:** Consumer lag · monitoring · Testcontainers Kafka · RabbitMQ exchanges/acks/DLX · Kafka vs RabbitMQ matrix

**Notes:**

### Week 11 · Reactive & Real-Time

#### Mon 14 Dec · D051 - Reactive foundations

- [ ] Done

*Week 11*

**Build:** Reactor operator playground

**Done when:** You can predict operator output before running

**Topics:** Reactive Streams spec · Mono/Flux · operators · cold vs hot · backpressure · schedulers

**Notes:**

#### Tue 15 Dec · D052 - Spring WebFlux

- [ ] Done

*Week 11*

**Build:** Port one read-heavy module to WebFlux

**Done when:** Non-blocking end to end, verified with BlockHound

**Topics:** Annotated vs functional endpoints · Netty · WebFlux vs MVC trade-offs · blocking-call traps

**Notes:**

#### Wed 16 Dec · D053 - Reactive data

- [ ] Done

*Week 11*

**Build:** Reactive repository for the ported module

**Done when:** Load test shows the actual (or absent) gain

**Topics:** R2DBC · reactive Redis · transactions in reactive · when reactive is the wrong choice

**Notes:**

#### Thu 17 Dec · D054 - HTTP clients & resilience

- [ ] Done

*Week 11*

**Build:** Resilient client for an external service

**Done when:** Slow dependency degrades, never hangs the app

**Topics:** WebClient · RestClient · timeouts · retries with backoff · error mapping · connection pooling

**Notes:**

#### Fri 18 Dec · D055 - Real-time delivery

- [ ] Done

*Week 11*

**Build:** Live order-status feed over SSE/WebSocket

**Done when:** Browser updates without polling

**Topics:** SSE · WebSockets · STOMP · session management · scaling websockets · heartbeats

**Notes:**

### Week 12 · Microservices I

#### Mon 21 Dec · D056 - Decomposition

- [ ] Done

*Week 12*

**Build:** Split the app into 2-3 services on paper

**Done when:** Boundaries justified by data ownership

**Topics:** Monolith vs modular monolith vs microservices · bounded contexts · DDD-lite · distributed-monolith smell

**Notes:**

#### Tue 22 Dec · D057 - Service discovery

- [ ] Done

*Week 12*

**Build:** Register two services and call by service name

**Done when:** No hardcoded host/port anywhere

**Topics:** Eureka/Consul · client-side load balancing · Spring Cloud LoadBalancer · health-based routing

**Notes:**

#### Wed 23 Dec · D058 - API Gateway

- [ ] Done

*Week 12*

**Build:** Gateway fronting both services

**Done when:** Single entry point, per-route auth + rate limit

**Topics:** Spring Cloud Gateway · predicates · filters · routing · auth at the edge · aggregation

**Notes:**

#### Thu 24 Dec · D059 - Centralised configuration

- [ ] Done

*Week 12*

**Build:** Externalise config for both services

**Done when:** Config change propagates without a rebuild

**Topics:** Spring Cloud Config · Git-backed config · refresh scope · encryption · config drift

**Notes:**

#### Fri 25 Dec · D060 - Resilience4j

- [ ] Done

*Week 12*

**Build:** Circuit-break every cross-service call

**Done when:** Killing a service degrades, never cascades

**Topics:** Circuit breaker states · retry · bulkhead · rate limiter · time limiter · fallbacks · tuning

**Notes:**

### Week 13 · Microservices II & Distributed Data

#### Mon 28 Dec · D061 - Sagas

- [ ] Done

*Week 13*

**Build:** Implement an order saga with compensation over Kafka

**Done when:** Failure at step 3 cleanly rolls the world back

**Topics:** Distributed transaction problem · choreography vs orchestration · compensating actions · saga state

**Notes:**

#### Tue 29 Dec · D062 - CQRS & event sourcing

- [ ] Done

*Week 13*

**Build:** Build a read projection for order history

**Done when:** Read model rebuilds from the event log

**Topics:** Read/write model separation · projections · event store · replay · eventual consistency · when it's overkill

**Notes:**

#### Wed 30 Dec · D063 - Distributed caching

- [ ] Done

*Week 13*

**Build:** Shared Redis cache layer across services

**Done when:** Documented invalidation strategy that holds

**Topics:** Cache-aside vs read-through vs write-behind · invalidation · hot keys · stampede protection · Redis Sentinel/Cluster

**Notes:**

#### Thu 31 Dec · D064 - Consistency & coordination

- [ ] Done

*Week 13*

**Build:** Make every write endpoint idempotent

**Done when:** A duplicate request creates exactly one record

**Topics:** CAP/PACELC · idempotent APIs · distributed locks (Redis/Redisson) · retries and duplicates · clock skew

**Notes:**

#### Fri 1 Jan · D065 - Service-to-service security

- [ ] Done

*Week 13*

**Build:** Secure inter-service calls with propagated identity

**Done when:** Direct service calls without identity are rejected

**Topics:** mTLS · service accounts · token propagation · zero-trust basics · internal vs external APIs

**Notes:**

### Week 14 · Observability & Operations

#### Mon 4 Jan · D066 - Actuator

- [ ] Done

*Week 14*

**Build:** Custom health indicators for DB/Kafka/Redis

**Done when:** /health truthfully reflects dependency state

**Topics:** Endpoints · health indicators & groups · liveness/readiness · info contributors · securing actuator

**Notes:**

#### Tue 5 Jan · D067 - Metrics

- [ ] Done

*Week 14*

**Build:** Dashboard: latency, throughput, error rate, saturation

**Done when:** RED + USE metrics visible on one board

**Topics:** Micrometer · counters/gauges/timers · tags · Prometheus scrape · Grafana

**Notes:**

#### Wed 6 Jan · D068 - Logging

- [ ] Done

*Week 14*

**Build:** Correlation ID across gateway > service > service

**Done when:** One ID traces a full request in the log store

**Topics:** Structured JSON logs · MDC + correlation IDs · log levels · sampling · PII scrubbing · Loki/ELK

**Notes:**

#### Thu 7 Jan · D069 - Distributed tracing

- [ ] Done

*Week 14*

**Build:** Trace one request across all services

**Done when:** Full waterfall visible with DB, Redis and Kafka spans

**Topics:** OpenTelemetry · Micrometer Tracing · spans/baggage · context propagation · Zipkin/Jaeger

**Notes:**

#### Fri 8 Jan · D070 - Running it in production

- [ ] Done

*Week 14*

**Build:** Define SLOs + 3 real alerts with runbooks

**Done when:** Alerts are actionable, not noisy

**Topics:** SLIs/SLOs · error budgets · alert design · runbooks · on-call · post-mortems

**Notes:**

### Week 15 · Containers, Kubernetes & CI/CD

#### Mon 11 Jan · D071 - Docker for Spring Boot

- [ ] Done

*Week 15*

**Build:** Dockerfile + buildpack image, compared on size

**Done when:** Image boots from a clean pull

**Topics:** Images vs containers · layered jars · multi-stage builds · Cloud Native Buildpacks · non-root user

**Notes:**

#### Tue 12 Jan · D072 - Local stack with Compose

- [ ] Done

*Week 15*

**Build:** One-command stack: app + Postgres + Redis + Kafka

**Done when:** docker compose up gives a working system

**Topics:** Compose networks/volumes · healthchecks · depends_on · seeding · dev vs test compose files

**Notes:**

#### Wed 13 Jan · D073 - Kubernetes essentials

- [ ] Done

*Week 15*

**Build:** Deploy the app to kind/minikube

**Done when:** App reachable through Ingress locally

**Topics:** Pods · Deployments · Services · Ingress · ConfigMaps · Secrets · namespaces · kubectl

**Notes:**

#### Thu 14 Jan · D074 - Spring Boot on K8s

- [ ] Done

*Week 15*

**Build:** Zero-downtime rolling deploy

**Done when:** Rollout with live traffic drops no requests

**Topics:** Probes · resource requests & limits · HPA · graceful shutdown · rolling updates

**Notes:**

#### Fri 15 Jan · D075 - CI/CD pipeline

- [ ] Done

*Week 15*

**Build:** Full pipeline from push to deployed image

**Done when:** Merge to main auto-deploys a tagged image

**Topics:** GitHub Actions · build > test > scan > image > deploy · caching · environments · secrets · versioning

**Notes:**

### Week 16 · Performance & Concurrency

#### Mon 18 Jan · D076 - Virtual threads & concurrency in Spring

- [ ] Done

*Week 16*

**Build:** Run the service on virtual threads and compare under load

**Done when:** Documented numbers for platform vs virtual threads

**Topics:** Virtual threads · pinning · thread-pool sizing · blocking vs non-blocking · structured concurrency

**Notes:**

#### Tue 19 Jan · D077 - Profiling & tuning

- [ ] Done

*Week 16*

**Build:** Profile under load, fix the top bottleneck

**Done when:** Documented before/after numbers

**Topics:** JFR · async-profiler · flame graphs · GC tuning for services · connection pool sizing · memory leaks

**Notes:**

#### Wed 20 Jan · D078 - Load testing

- [ ] Done

*Week 16*

**Build:** Load-test the critical path, find the knee

**Done when:** p99 latency and breaking point known

**Topics:** k6/JMeter/Gatling · open vs closed models · percentiles over averages · capacity planning

**Notes:**

#### Thu 21 Jan · D079 - Caching & delivery end to end

- [ ] Done

*Week 16*

**Build:** Add HTTP caching + ETags to read endpoints

**Done when:** Repeat reads return 304 and cost nothing

**Topics:** HTTP caching headers · ETags · CDN · multi-level caching (local + Redis) · compression

**Notes:**

#### Fri 22 Jan · D080 - Startup & footprint

- [ ] Done

*Week 16*

**Build:** Build a native image, measure startup + memory

**Done when:** Native binary boots in ms with a documented trade-off list

**Topics:** Spring AOT · GraalVM native image · lazy init · class data sharing

**Notes:**

### Week 17 · Architecture & Phase 1 Consolidation

#### Mon 25 Jan · D081 - AOP & proxies

- [ ] Done

*Week 17*

**Build:** Write an audit/timing aspect

**Done when:** You can explain proxying and its limits cold

**Topics:** Aspects · pointcuts · JDK vs CGLIB proxies · why @Transactional and @Async break on self-invocation

**Notes:**

#### Tue 26 Jan · D082 - Hexagonal architecture & Spring Modulith

- [ ] Done

*Week 17*

**Build:** Restructure the service into verified modules

**Done when:** Architecture test fails when a boundary is crossed

**Topics:** Ports and adapters · module boundaries · Spring Modulith · architecture tests

**Notes:**

#### Wed 27 Jan · D083 - GraphQL & gRPC

- [ ] Done

*Week 17*

**Build:** Expose one read path over GraphQL and one call over gRPC

**Done when:** You can say when each style fits

**Topics:** Spring for GraphQL · schema-first design · gRPC + Protobuf · REST vs GraphQL vs gRPC

**Notes:**

#### Thu 28 Jan · D084 - Code quality, docs & ADRs

- [ ] Done

*Week 17*

**Build:** Quality pass + ADRs for the major decisions so far

**Done when:** Quality gate green, decisions written down

**Topics:** SonarQube · static analysis · README/docs · ADRs · tech-debt register

**Notes:**

#### Fri 29 Jan · D085 - Phase 1 consolidation

- [ ] Done

*Week 17*

**Build:** Architecture doc + 5-minute demo of the practice service

**Done when:** A new dev could run and understand it unaided

**Topics:** Architecture review · end-to-end demo · gap list

**Notes:**

---

## Phase 2 - AI Systems & Spring AI

*20 days*

> Understand AI systems well enough to add one to a backend for a real reason, and to defend that choice in an interview.

### Week 18 · AI Foundations for Backend Engineers

#### Mon 1 Feb · D086 - How LLMs actually work

- [ ] Done

*Week 18*

**Build:** Call a model directly over HTTP, inspect token usage

**Done when:** You can estimate cost/latency of a feature

**Topics:** Tokens · context windows · temperature/top-p · streaming · latency & cost model · model families

**Notes:**

#### Tue 2 Feb · D087 - Prompt engineering

- [ ] Done

*Week 18*

**Build:** Prompt library with versioned templates

**Done when:** The same prompt gives stable, parseable output

**Topics:** System vs user prompts · few-shot · chain-of-thought · structured/JSON output · prompt versioning

**Notes:**

#### Wed 3 Feb · D088 - Embeddings & vector search

- [ ] Done

*Week 18*

**Build:** Embed a document set, run similarity search

**Done when:** Semantic search returns sensible neighbours

**Topics:** Embedding models · cosine similarity · dimensions · ANN indexes (HNSW/IVF) · vector DB landscape

**Notes:**

#### Thu 4 Feb · D089 - RAG architecture

- [ ] Done

*Week 18*

**Build:** Whiteboard the full RAG pipeline for your project

**Done when:** Diagram covers every stage and its failure mode

**Topics:** Ingest > chunk > embed > store > retrieve > augment > generate · failure modes · RAG vs fine-tuning

**Notes:**

#### Fri 5 Feb · D090 - AI system design

- [ ] Done

*Week 18*

**Build:** Design doc for the AI feature you'll actually ship

**Done when:** Use case justified, not bolted on

**Topics:** Latency budgets · caching · fallbacks · streaming UX · async inference · cost control · model routing · evaluation-first mindset

**Notes:**

### Week 19 · Spring AI Core

#### Mon 8 Feb · D091 - Spring AI setup

- [ ] Done

*Week 19*

**Build:** First ChatClient endpoint in the Spring app

**Done when:** Provider swappable via config only

**Topics:** Dependencies & starters · model abstraction · ChatClient · provider config · swapping providers

**Notes:**

#### Tue 9 Feb · D092 - Prompts & structured output

- [ ] Done

*Week 19*

**Build:** Endpoint returning a strongly-typed AI response

**Done when:** Response deserialises into a record every time

**Topics:** PromptTemplate · system prompts · output converters · mapping to POJOs · retries on malformed output

**Notes:**

#### Wed 10 Feb · D093 - Memory & advisors

- [ ] Done

*Week 19*

**Build:** Multi-turn conversation endpoint with persistence

**Done when:** Conversation survives an app restart

**Topics:** Chat memory stores · conversation IDs · the advisor chain · context-window management · summarisation

**Notes:**

#### Thu 11 Feb · D094 - Streaming responses

- [ ] Done

*Week 19*

**Build:** Stream tokens to the browser as they arrive

**Done when:** First token visible in well under a second

**Topics:** Reactive streaming from the model · SSE to the client · partial rendering · cancellation · timeouts

**Notes:**

#### Fri 12 Feb · D095 - Multimodal & other models

- [ ] Done

*Week 19*

**Build:** Add one multimodal capability to the app

**Done when:** Feature works with proper error handling

**Topics:** Image/audio/vision models · transcription · image generation · model-specific options

**Notes:**

### Week 20 · RAG Inside Spring AI

#### Mon 15 Feb · D096 - Ingestion pipeline

- [ ] Done

*Week 20*

**Build:** ETL job that ingests your document corpus

**Done when:** Corpus ingested with clean metadata

**Topics:** DocumentReader (PDF/Markdown/HTML) · transformers · splitters · chunk size/overlap · metadata

**Notes:**

#### Tue 16 Feb · D097 - Embeddings & vector store

- [ ] Done

*Week 20*

**Build:** pgvector store wired into the app

**Done when:** Similarity queries run against real data

**Topics:** EmbeddingModel · VectorStore API · pgvector (and Redis as a vector store) · indexing · re-embedding on change

**Notes:**

#### Wed 17 Feb · D098 - Retrieval tuning

- [ ] Done

*Week 20*

**Build:** Tune retrieval and measure the difference

**Done when:** Retrieval quality improved on a fixed test set

**Topics:** Chunking strategies · top-k · similarity threshold · metadata filters · hybrid (keyword + vector) search

**Notes:**

#### Thu 18 Feb · D099 - RAG orchestration

- [ ] Done

*Week 20*

**Build:** Production-shaped RAG endpoint with citations

**Done when:** Every answer cites its source chunks

**Topics:** Question-answer advisor · query rewriting/expansion · re-ranking · citations · multi-document synthesis

**Notes:**

#### Fri 19 Feb · D100 - Grounding & guardrails

- [ ] Done

*Week 20*

**Build:** Guardrail layer around the RAG endpoint

**Done when:** An out-of-corpus question gets an honest "I don't know"

**Topics:** Hallucination mitigation · refusal on low confidence · PII redaction · content filtering · input sanitisation

**Notes:**

### Week 21 · Tools, Agents & MCP in Spring AI

#### Mon 22 Feb · D101 - Tool / function calling

- [ ] Done

*Week 21*

**Build:** Expose 3 real app operations as AI tools

**Done when:** The model performs a real action correctly

**Topics:** Tool definitions · @Tool · argument schemas · execution loop · safety of side-effecting tools

**Notes:**

#### Tue 23 Feb · D102 - Agentic patterns

- [ ] Done

*Week 21*

**Build:** Implement a routing + evaluator workflow

**Done when:** The workflow terminates safely, always

**Topics:** Chaining · routing · parallelisation · evaluator-optimiser · orchestrator-worker · loop limits · human-in-the-loop

**Notes:**

#### Wed 24 Feb · D103 - Model Context Protocol

- [ ] Done

*Week 21*

**Build:** Expose part of the app as an MCP server

**Done when:** An external MCP client can use your tools

**Topics:** MCP concepts · servers/clients · resources vs tools · Spring AI MCP integration · transports

**Notes:**

#### Thu 25 Feb · D104 - Async & batch AI workloads

- [ ] Done

*Week 21*

**Build:** Long-running AI job via Kafka + status polling

**Done when:** Slow inference never blocks a web thread

**Topics:** Queue-backed inference over Kafka · job status endpoints · retries/backoff · provider timeouts

**Notes:**

#### Fri 26 Feb · D105 - Cost, quotas & routing

- [ ] Done

*Week 21*

**Build:** Token/cost metrics + a cheap-to-strong routing rule

**Done when:** Per-request cost visible in Grafana

**Topics:** Token accounting · rate limits · prompt/response caching in Redis · cheap-model-first routing · provider failover

**Notes:**

---

## Phase 3 - Langchain4j & Productionising AI

*15 days*

> Learn the second major Java AI toolkit well enough to choose between the two on merit, then make the AI features production-safe.

### Week 22 · Langchain4j Core & RAG

#### Mon 1 Mar · D106 - Langchain4j fundamentals

- [ ] Done

*Week 22*

**Build:** First Langchain4j chat endpoint in the Spring app

**Done when:** Provider swappable via config only

**Topics:** Library structure · model abstraction · ChatModel · provider config · Spring Boot starter

**Notes:**

#### Tue 2 Mar · D107 - AiServices, prompts & structured output

- [ ] Done

*Week 22*

**Build:** Declarative AI service returning typed results

**Done when:** The AI interface is used like any other bean

**Topics:** AiServices interfaces · @SystemMessage/@UserMessage · templates · mapping to POJOs

**Notes:**

#### Wed 3 Mar · D108 - Memory & streaming

- [ ] Done

*Week 22*

**Build:** Multi-user chat with persistent memory and streamed output

**Done when:** Two users never see each other's history

**Topics:** ChatMemory and stores · memory per user · streaming responses · token-window management

**Notes:**

#### Thu 4 Mar · D109 - RAG: ingestion & embedding stores

- [ ] Done

*Week 22*

**Build:** Ingest the corpus and answer questions over it

**Done when:** Answers come from your documents

**Topics:** Document loaders and splitters · EmbeddingModel · EmbeddingStore (pgvector/Redis) · ContentRetriever

**Notes:**

#### Fri 5 Mar · D110 - Advanced RAG

- [ ] Done

*Week 22*

**Build:** Add query rewriting and re-ranking to the pipeline

**Done when:** Measured quality gain over naive retrieval

**Topics:** RetrievalAugmentor · query transformers · routers · re-ranking · metadata filtering

**Notes:**

### Week 23 · Agents, Tools & Comparison

#### Mon 8 Mar · D111 - Tools

- [ ] Done

*Week 23*

**Build:** Expose the same 3 app operations as Langchain4j tools

**Done when:** The model performs a real action correctly

**Topics:** @Tool methods · argument handling · tool errors · side-effect safety

**Notes:**

#### Tue 9 Mar · D112 - Agents & workflows

- [ ] Done

*Week 23*

**Build:** Build a small multi-step agent workflow

**Done when:** The workflow terminates safely, always

**Topics:** Agentic workflows · sequential/parallel/conditional composition · supervisor pattern · loop limits

**Notes:**

#### Wed 10 Mar · D113 - MCP & Spring Boot integration

- [ ] Done

*Week 23*

**Build:** Consume an MCP server from a Langchain4j service

**Done when:** The agent calls an external tool through MCP

**Topics:** MCP client/server in Langchain4j · wiring into Spring beans · config · testing

**Notes:**

#### Thu 11 Mar · D114 - Guardrails

- [ ] Done

*Week 23*

**Build:** Add input/output guardrails to the AI service

**Done when:** Bad input and bad output are both caught

**Topics:** Input and output guardrails · validation · retries on bad output · content policies

**Notes:**

#### Fri 12 Mar · D115 - Spring AI vs Langchain4j

- [ ] Done

*Week 23*

**Build:** Build the same feature in both and compare

**Done when:** A written decision on which to use when, backed by your own code

**Topics:** Feature matrix · ergonomics · ecosystem fit · portability · lock-in

**Notes:**

### Week 24 · Productionising AI

#### Mon 15 Mar · D116 - Evaluation

- [ ] Done

*Week 24*

**Build:** Eval harness with 30+ golden questions

**Done when:** Prompt changes are scored, not guessed

**Topics:** Golden datasets · relevancy & faithfulness evaluators · LLM-as-judge · regression suites · offline vs online eval

**Notes:**

#### Tue 16 Mar · D117 - Testing AI features

- [ ] Done

*Week 24*

**Build:** AI feature tests that run in CI without a provider

**Done when:** CI is green with zero external AI calls

**Topics:** Deterministic tests · mocking model calls · recorded fixtures · contract tests for tool schemas

**Notes:**

#### Wed 17 Mar · D118 - AI observability & cost

- [ ] Done

*Week 24*

**Build:** Trace + metrics for every model call

**Done when:** One trace shows retrieval + model + tool spans

**Topics:** Token/latency metrics · tracing model calls · prompt & response logging with redaction · feedback capture

**Notes:**

#### Thu 18 Mar · D119 - AI security

- [ ] Done

*Week 24*

**Build:** Injection test suite + fixes

**Done when:** Known injection payloads fail against your app

**Topics:** Prompt injection · indirect injection via documents · data exfiltration · tenant isolation of vector data · output handling

**Notes:**

#### Fri 19 Mar · D120 - Phase 3 consolidation

- [ ] Done

*Week 24*

**Build:** AI architecture doc + 5-minute demo

**Done when:** You can defend every AI design decision

**Topics:** Architecture review of the AI subsystem · cost/benefit write-up · documentation · demo script

**Notes:**

---

## Phase 4 - Interview Preparation

*10 days*

> Convert 120 days of building into answers that land. Development-side prep only - DSA, Fundamentals and System Design have their own tabs.

### Week 25 · Core Revision

#### Mon 22 Mar · D121 - Java & JVM rapid fire

- [ ] Done

*Week 25*

**Build:** 100-question self-quiz, written answers

**Done when:** Under 60s per answer, out loud

**Topics:** Collections internals · concurrency · memory model · GC · streams · immutability · trick questions

**Notes:**

#### Tue 23 Mar · D122 - Spring internals rapid fire

- [ ] Done

*Week 25*

**Build:** 100-question self-quiz, written answers

**Done when:** You can explain proxying and its limits cold

**Topics:** IoC/DI · bean lifecycle · auto-config · AOP & proxies · @Transactional internals · filter chain · gotchas

**Notes:**

#### Wed 24 Mar · D123 - Databases & JPA

- [ ] Done

*Week 25*

**Build:** 25 SQL problems + 25 JPA/DB questions

**Done when:** You can write and optimise a non-trivial query live

**Topics:** Indexing · query plans · joins · isolation anomalies · locking · N+1 · sharding/replication · SQL practice

**Notes:**

#### Thu 25 Mar · D124 - Kafka & Redis drill

- [ ] Done

*Week 25*

**Build:** 40 Kafka + Redis questions, answered aloud

**Done when:** Every answer references something you built

**Topics:** Partitions · offsets · rebalancing · delivery semantics · outbox · DLT; Redis structures · eviction · persistence · locks · cache patterns

**Notes:**

#### Fri 26 Mar · D125 - Microservices & distributed systems

- [ ] Done

*Week 25*

**Build:** Failure-scenario drill: 15 "what breaks and why"

**Done when:** Answers reference your own project

**Topics:** Sagas · outbox · idempotency · CAP · observability · deployment strategies · failure scenarios

**Notes:**

### Week 26 · Mocks, Story & Close

#### Mon 29 Mar · D126 - Low-level design with Spring

- [ ] Done

*Week 26*

**Build:** Two LLD designs, coded and explained

**Done when:** Both finished within time, with clean abstractions

**Topics:** Class design · patterns · SOLID applied · e.g. parking lot / rate limiter / notification service

**Notes:**

#### Tue 30 Mar · D127 - AI engineering questions

- [ ] Done

*Week 26*

**Build:** 30 AI-backend questions, answered aloud

**Done when:** You can defend your AI design choices with numbers

**Topics:** RAG vs fine-tuning · chunking · evaluation · agents · guardrails · Spring AI vs Langchain4j · cost control

**Notes:**

#### Wed 31 Mar · D128 - Project deep dive

- [ ] Done

*Week 26*

**Build:** 4-minute project pitch + 10 follow-up answers

**Done when:** The pitch is crisp and provokes good questions

**Topics:** Architecture narration · key decisions & trade-offs · hardest bug · metrics/impact · STAR stories

**Notes:**

#### Thu 1 Apr · D129 - Mock - technical round

- [ ] Done

*Week 26*

**Build:** Record a 60-minute mock, then self-review

**Done when:** Written list of 5 concrete fixes

**Topics:** Live coding on the codebase · debugging · code review · API design questions

**Notes:**

#### Fri 2 Apr · D130 - Final - behavioural & close

- [ ] Done

*Week 26*

**Build:** Ship the final portfolio + write the next plan

**Done when:** Everything public, polished and honest

**Topics:** Behavioural stories · resume & LinkedIn polish · GitHub README pass · next-90-days plan

**Notes:**
