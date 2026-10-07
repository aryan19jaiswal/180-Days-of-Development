# 🦇 180 Days of Batman - Development Roadmap

**Spring Boot → Kafka & Redis → Spring AI → Langchain4j.** Monday to Friday, 130 build-days over 26 weeks, Oct 5, 2026 → Apr 2, 2027. About 2 hours a day (~30 min learn + ~90 min build). Every day ends with something that compiles and runs.

## How to track progress

Flip the box when a day's *Done when* check passes, and strike the title through if you like:

```md
- [ ] **D003 · Bean lifecycle & scopes**     <- todo
- [x] **~~D003 · Bean lifecycle & scopes~~** <- done
```

## Phases

- [ ] **PHASE 1 - Spring Boot & Industry Practice** (D001-D085, 85 days)
- [ ] **PHASE 2 - AI Systems & Spring AI** (D086-D105, 20 days)
- [ ] **PHASE 3 - Langchain4j & Productionising AI** (D106-D120, 15 days)
- [ ] **PHASE 4 - Interview Preparation** (D121-D130, 10 days)

Full topic lists live in [`Development.md`](Development.md).


---

## PHASE 1 - Spring Boot & Industry Practice


### Week 01 - Spring Core & Boot Foundations

- [x] **D001 · The Spring container** (2026-10-05, Mon)
    - Build: Scaffold the practice service and wire a service layer through the container
    - Done when: Zero `new` for services; you can explain what the container builds at startup
- [x] **D002 · Dependency injection** (2026-10-06, Tue)
    - Build: Refactor to constructor injection everywhere
    - Done when: No `@Autowired` on fields
- [x] **D003 · Bean lifecycle & scopes** (2026-10-07, Wed)
    - Build: Custom `BeanPostProcessor` that times bean init
    - Done when: You can narrate the full lifecycle from memory
- [ ] **D004 · Spring Boot, demystified** (2026-10-08, Thu)
    - Build: Write your own tiny auto-configuration starter
    - Done when: Your starter auto-configures in a fresh app
- [ ] **D005 · Configuration & profiles** (2026-10-09, Fri)
    - Build: Multi-profile config (local/dev/prod)
    - Done when: App boots correctly under all three profiles

### Week 02 - Web Layer & API Design

- [ ] **D006 · Spring MVC architecture** (2026-10-12, Mon)
    - Build: Trace one request through the whole chain with logs
    - Done when: You can draw the request lifecycle unaided
- [ ] **D007 · Building REST controllers** (2026-10-13, Tue)
    - Build: CRUD API for the order domain
    - Done when: All endpoints work in Postman/HTTPie
- [ ] **D008 · DTOs, mapping & validation** (2026-10-14, Wed)
    - Build: Full DTO layer + validation with clean 400 responses
    - Done when: No entity ever crosses the controller boundary
- [ ] **D009 · Error handling contract** (2026-10-15, Thu)
    - Build: One global error handler, consistent error body
    - Done when: Every failure path returns the same JSON shape
- [ ] **D010 · API design discipline** (2026-10-16, Fri)
    - Build: Paged + filterable endpoints with a published OpenAPI spec
    - Done when: Swagger UI documents the whole API accurately

### Week 03 - Persistence with JPA

- [ ] **D011 · Spring Data JPA basics** (2026-10-19, Mon)
    - Build: Repositories on real Postgres
    - Done when: All CRUD passes against real Postgres
- [ ] **D012 · Relationship mapping** (2026-10-20, Tue)
    - Build: Model Order > OrderItem > Product properly
    - Done when: Bidirectional relations stay consistent both ways
- [ ] **D013 · Querying** (2026-10-21, Wed)
    - Build: Dynamic multi-filter search with `Specification`
    - Done when: One endpoint handles 5 optional filters
- [ ] **D014 · Transactions** (2026-10-22, Thu)
    - Build: Money-transfer-style service with correct rollback
    - Done when: Failure mid-way leaves zero partial state
- [ ] **D015 · JPA performance** (2026-10-23, Fri)
    - Build: Find and kill every N+1 with SQL logging on
    - Done when: Query count per endpoint is asserted in a test

### Week 04 - Data Layer & Redis

- [ ] **D016 · Schema migrations** (2026-10-26, Mon)
    - Build: Move the whole schema into Flyway migrations
    - Done when: Fresh DB builds itself from V1 upward
- [ ] **D017 · Auditing & locking** (2026-10-27, Tue)
    - Build: Auditing + optimistic locking on Order
    - Done when: Concurrent update test fails loudly, not silently
- [ ] **D018 · Redis & Spring Cache** (2026-10-28, Wed)
    - Build: Cache the product catalogue in Redis
    - Done when: Cache hit ratio visible, invalidation correct
- [ ] **D019 · Redis patterns** (2026-10-29, Thu)
    - Build: Redis-backed rate limiter + a sorted-set leaderboard
    - Done when: Limiter holds under concurrent calls
- [ ] **D020 · MongoDB & polyglot persistence** (2026-10-30, Fri)
    - Build: Store audit/event logs in MongoDB
    - Done when: Two datasources coexist without conflict

### Week 05 - Testing Like a Professional

- [ ] **D021 · Unit testing services** (2026-11-02, Mon)
    - Build: Full unit-test suite for the service layer
    - Done when: Services tested with zero Spring context
- [ ] **D022 · Slice tests** (2026-11-03, Tue)
    - Build: Controller and repository slice tests
    - Done when: Slice suite runs in seconds, not minutes
- [ ] **D023 · Integration testing** (2026-11-04, Wed)
    - Build: End-to-end test against real containers
    - Done when: Full journey test green on a clean machine
- [ ] **D024 · Testing external dependencies** (2026-11-05, Thu)
    - Build: Stub a third-party API with WireMock
    - Done when: No test touches the real internet
- [ ] **D025 · Test strategy & CI** (2026-11-06, Fri)
    - Build: Wire tests + coverage gate into GitHub Actions
    - Done when: PR fails if coverage or tests fail

### Week 06 - Spring Security I

- [ ] **D026 · Security architecture** (2026-11-09, Mon)
    - Build: Lock down the API with a minimal config
    - Done when: Unauthenticated calls get 401, not 500
- [ ] **D027 · Authentication** (2026-11-10, Tue)
    - Build: DB-backed user store with registration + login
    - Done when: Passwords hashed, never logged
- [ ] **D028 · JWT authentication** (2026-11-11, Wed)
    - Build: Full JWT login/refresh/logout flow
    - Done when: Expired/tampered tokens rejected correctly
- [ ] **D029 · Authorization** (2026-11-12, Thu)
    - Build: Role-based + ownership-based access rules
    - Done when: User A can never touch User B's data
- [ ] **D030 · OAuth2 & OIDC** (2026-11-13, Fri)
    - Build: Add social login + resource-server validation
    - Done when: Login via provider issues a valid app session

### Week 07 - Spring Security II & Hardening

- [ ] **D031 · Web hardening** (2026-11-16, Mon)
    - Build: Correct CORS + header policy for a real SPA client
    - Done when: Browser client works with no wildcard CORS
- [ ] **D032 · Abuse protection** (2026-11-17, Tue)
    - Build: Per-user + per-IP rate limiting
    - Done when: 429s returned with `Retry-After`
- [ ] **D033 · OWASP Top 10 for Spring** (2026-11-18, Wed)
    - Build: Threat-model your own API and fix 3 real gaps
    - Done when: Dependency scan clean of high CVEs
- [ ] **D034 · Access-control modelling** (2026-11-19, Thu)
    - Build: Add tenant isolation to the data layer
    - Done when: Cross-tenant read is impossible by construction
- [ ] **D035 · Secrets & security testing** (2026-11-20, Fri)
    - Build: Move all secrets out of the repo + write authz tests
    - Done when: Repo has zero secrets, authz tests pass

### Week 08 - Async, Scheduling & Batch

- [ ] **D036 · Application events** (2026-11-23, Mon)
    - Build: Emit domain events on order state changes
    - Done when: Side effects removed from the core service
- [ ] **D037 · Async execution** (2026-11-24, Tue)
    - Build: Async email/notification dispatch
    - Done when: Request latency unaffected by slow side work
- [ ] **D038 · Scheduling** (2026-11-25, Wed)
    - Build: Nightly report job, safe across 2 instances
    - Done when: Job runs exactly once with 2 instances up
- [ ] **D039 · Spring Batch I** (2026-11-26, Thu)
    - Build: Batch import of a large CSV into the DB
    - Done when: 1M rows imported with restart-on-failure
- [ ] **D040 · Spring Batch II** (2026-11-27, Fri)
    - Build: Partition the import job, measure the speedup
    - Done when: Throughput improves and failures are skippable

### Week 09 - Kafka I

- [ ] **D041 · Messaging fundamentals** (2026-11-30, Mon)
    - Build: Design note: which order flows should be async and why
    - Done when: Decision documented with trade-offs
- [ ] **D042 · Kafka fundamentals** (2026-12-01, Tue)
    - Build: Run Kafka locally and produce/consume with the CLI tools
    - Done when: You can explain partition, offset and consumer group aloud
- [ ] **D043 · Producers with Spring Kafka** (2026-12-02, Wed)
    - Build: Publish order events keyed by order id
    - Done when: Events for one order land in one partition, in order
- [ ] **D044 · Consumers** (2026-12-03, Thu)
    - Build: Consume order events in a group of 3
    - Done when: Scaling consumers splits partitions with no duplicates
- [ ] **D045 · Failure handling** (2026-12-04, Fri)
    - Build: Add retry topic + dead-letter topic handling
    - Done when: A poison message can't stall the consumer

### Week 10 - Kafka II & Messaging Reliability

- [ ] **D046 · Idempotent consumers & transactions** (2026-12-07, Mon)
    - Build: Make the order consumer idempotent
    - Done when: Replaying the topic creates no duplicates
- [ ] **D047 · Transactional outbox** (2026-12-08, Tue)
    - Build: Implement the outbox pattern for order events
    - Done when: No event lost when the DB commits and the broker fails
- [ ] **D048 · Schema evolution** (2026-12-09, Wed)
    - Build: Evolve an event schema without breaking consumers
    - Done when: An old consumer reads the new event
- [ ] **D049 · Kafka Streams & Connect** (2026-12-10, Thu)
    - Build: Per-minute order aggregation with Kafka Streams
    - Done when: Live count matches the source data
- [ ] **D050 · Kafka ops, testing & RabbitMQ** (2026-12-11, Fri)
    - Build: Test the flow with Testcontainers; run one flow on RabbitMQ
    - Done when: You can justify choosing one over the other

### Week 11 - Reactive & Real-Time

- [ ] **D051 · Reactive foundations** (2026-12-14, Mon)
    - Build: Reactor operator playground
    - Done when: You can predict operator output before running
- [ ] **D052 · Spring WebFlux** (2026-12-15, Tue)
    - Build: Port one read-heavy module to WebFlux
    - Done when: Non-blocking end to end, verified with BlockHound
- [ ] **D053 · Reactive data** (2026-12-16, Wed)
    - Build: Reactive repository for the ported module
    - Done when: Load test shows the actual (or absent) gain
- [ ] **D054 · HTTP clients & resilience** (2026-12-17, Thu)
    - Build: Resilient client for an external service
    - Done when: Slow dependency degrades, never hangs the app
- [ ] **D055 · Real-time delivery** (2026-12-18, Fri)
    - Build: Live order-status feed over SSE/WebSocket
    - Done when: Browser updates without polling

### Week 12 - Microservices I

- [ ] **D056 · Decomposition** (2026-12-21, Mon)
    - Build: Split the app into 2-3 services on paper
    - Done when: Boundaries justified by data ownership
- [ ] **D057 · Service discovery** (2026-12-22, Tue)
    - Build: Register two services and call by service name
    - Done when: No hardcoded host/port anywhere
- [ ] **D058 · API Gateway** (2026-12-23, Wed)
    - Build: Gateway fronting both services
    - Done when: Single entry point, per-route auth + rate limit
- [ ] **D059 · Centralised configuration** (2026-12-24, Thu)
    - Build: Externalise config for both services
    - Done when: Config change propagates without a rebuild
- [ ] **D060 · Resilience4j** (2026-12-25, Fri)
    - Build: Circuit-break every cross-service call
    - Done when: Killing a service degrades, never cascades

### Week 13 - Microservices II & Distributed Data

- [ ] **D061 · Sagas** (2026-12-28, Mon)
    - Build: Implement an order saga with compensation over Kafka
    - Done when: Failure at step 3 cleanly rolls the world back
- [ ] **D062 · CQRS & event sourcing** (2026-12-29, Tue)
    - Build: Build a read projection for order history
    - Done when: Read model rebuilds from the event log
- [ ] **D063 · Distributed caching** (2026-12-30, Wed)
    - Build: Shared Redis cache layer across services
    - Done when: Documented invalidation strategy that holds
- [ ] **D064 · Consistency & coordination** (2026-12-31, Thu)
    - Build: Make every write endpoint idempotent
    - Done when: A duplicate request creates exactly one record
- [ ] **D065 · Service-to-service security** (2027-01-01, Fri)
    - Build: Secure inter-service calls with propagated identity
    - Done when: Direct service calls without identity are rejected

### Week 14 - Observability & Operations

- [ ] **D066 · Actuator** (2027-01-04, Mon)
    - Build: Custom health indicators for DB/Kafka/Redis
    - Done when: `/health` truthfully reflects dependency state
- [ ] **D067 · Metrics** (2027-01-05, Tue)
    - Build: Dashboard: latency, throughput, error rate, saturation
    - Done when: RED + USE metrics visible on one board
- [ ] **D068 · Logging** (2027-01-06, Wed)
    - Build: Correlation ID across gateway > service > service
    - Done when: One ID traces a full request in the log store
- [ ] **D069 · Distributed tracing** (2027-01-07, Thu)
    - Build: Trace one request across all services
    - Done when: Full waterfall visible with DB, Redis and Kafka spans
- [ ] **D070 · Running it in production** (2027-01-08, Fri)
    - Build: Define SLOs + 3 real alerts with runbooks
    - Done when: Alerts are actionable, not noisy

### Week 15 - Containers, Kubernetes & CI/CD

- [ ] **D071 · Docker for Spring Boot** (2027-01-11, Mon)
    - Build: Dockerfile + buildpack image, compared on size
    - Done when: Image boots from a clean pull
- [ ] **D072 · Local stack with Compose** (2027-01-12, Tue)
    - Build: One-command stack: app + Postgres + Redis + Kafka
    - Done when: `docker compose up` gives a working system
- [ ] **D073 · Kubernetes essentials** (2027-01-13, Wed)
    - Build: Deploy the app to kind/minikube
    - Done when: App reachable through Ingress locally
- [ ] **D074 · Spring Boot on K8s** (2027-01-14, Thu)
    - Build: Zero-downtime rolling deploy
    - Done when: Rollout with live traffic drops no requests
- [ ] **D075 · CI/CD pipeline** (2027-01-15, Fri)
    - Build: Full pipeline from push to deployed image
    - Done when: Merge to main auto-deploys a tagged image

### Week 16 - Performance & Concurrency

- [ ] **D076 · Virtual threads & concurrency in Spring** (2027-01-18, Mon)
    - Build: Run the service on virtual threads and compare under load
    - Done when: Documented numbers for platform vs virtual threads
- [ ] **D077 · Profiling & tuning** (2027-01-19, Tue)
    - Build: Profile under load, fix the top bottleneck
    - Done when: Documented before/after numbers
- [ ] **D078 · Load testing** (2027-01-20, Wed)
    - Build: Load-test the critical path, find the knee
    - Done when: p99 latency and breaking point known
- [ ] **D079 · Caching & delivery end to end** (2027-01-21, Thu)
    - Build: Add HTTP caching + ETags to read endpoints
    - Done when: Repeat reads return 304 and cost nothing
- [ ] **D080 · Startup & footprint** (2027-01-22, Fri)
    - Build: Build a native image, measure startup + memory
    - Done when: Native binary boots in ms with a documented trade-off list

### Week 17 - Architecture & Phase 1 Consolidation

- [ ] **D081 · AOP & proxies** (2027-01-25, Mon)
    - Build: Write an audit/timing aspect
    - Done when: You can explain proxying and its limits cold
- [ ] **D082 · Hexagonal architecture & Spring Modulith** (2027-01-26, Tue)
    - Build: Restructure the service into verified modules
    - Done when: Architecture test fails when a boundary is crossed
- [ ] **D083 · GraphQL & gRPC** (2027-01-27, Wed)
    - Build: Expose one read path over GraphQL and one call over gRPC
    - Done when: You can say when each style fits
- [ ] **D084 · Code quality, docs & ADRs** (2027-01-28, Thu)
    - Build: Quality pass + ADRs for the major decisions so far
    - Done when: Quality gate green, decisions written down
- [ ] **D085 · Phase 1 consolidation** (2027-01-29, Fri)
    - Build: Architecture doc + 5-minute demo of the practice service
    - Done when: A new dev could run and understand it unaided

---

## PHASE 2 - AI Systems & Spring AI


### Week 18 - AI Foundations for Backend Engineers

- [ ] **D086 · How LLMs actually work** (2027-02-01, Mon)
    - Build: Call a model directly over HTTP, inspect token usage
    - Done when: You can estimate cost/latency of a feature
- [ ] **D087 · Prompt engineering** (2027-02-02, Tue)
    - Build: Prompt library with versioned templates
    - Done when: The same prompt gives stable, parseable output
- [ ] **D088 · Embeddings & vector search** (2027-02-03, Wed)
    - Build: Embed a document set, run similarity search
    - Done when: Semantic search returns sensible neighbours
- [ ] **D089 · RAG architecture** (2027-02-04, Thu)
    - Build: Whiteboard the full RAG pipeline for your project
    - Done when: Diagram covers every stage and its failure mode
- [ ] **D090 · AI system design** (2027-02-05, Fri)
    - Build: Design doc for the AI feature you'll actually ship
    - Done when: Use case justified, not bolted on

### Week 19 - Spring AI Core

- [ ] **D091 · Spring AI setup** (2027-02-08, Mon)
    - Build: First `ChatClient` endpoint in the Spring app
    - Done when: Provider swappable via config only
- [ ] **D092 · Prompts & structured output** (2027-02-09, Tue)
    - Build: Endpoint returning a strongly-typed AI response
    - Done when: Response deserialises into a record every time
- [ ] **D093 · Memory & advisors** (2027-02-10, Wed)
    - Build: Multi-turn conversation endpoint with persistence
    - Done when: Conversation survives an app restart
- [ ] **D094 · Streaming responses** (2027-02-11, Thu)
    - Build: Stream tokens to the browser as they arrive
    - Done when: First token visible in well under a second
- [ ] **D095 · Multimodal & other models** (2027-02-12, Fri)
    - Build: Add one multimodal capability to the app
    - Done when: Feature works with proper error handling

### Week 20 - RAG Inside Spring AI

- [ ] **D096 · Ingestion pipeline** (2027-02-15, Mon)
    - Build: ETL job that ingests your document corpus
    - Done when: Corpus ingested with clean metadata
- [ ] **D097 · Embeddings & vector store** (2027-02-16, Tue)
    - Build: pgvector store wired into the app
    - Done when: Similarity queries run against real data
- [ ] **D098 · Retrieval tuning** (2027-02-17, Wed)
    - Build: Tune retrieval and measure the difference
    - Done when: Retrieval quality improved on a fixed test set
- [ ] **D099 · RAG orchestration** (2027-02-18, Thu)
    - Build: Production-shaped RAG endpoint with citations
    - Done when: Every answer cites its source chunks
- [ ] **D100 · Grounding & guardrails** (2027-02-19, Fri)
    - Build: Guardrail layer around the RAG endpoint
    - Done when: An out-of-corpus question gets an honest "I don't know"

### Week 21 - Tools, Agents & MCP in Spring AI

- [ ] **D101 · Tool / function calling** (2027-02-22, Mon)
    - Build: Expose 3 real app operations as AI tools
    - Done when: The model performs a real action correctly
- [ ] **D102 · Agentic patterns** (2027-02-23, Tue)
    - Build: Implement a routing + evaluator workflow
    - Done when: The workflow terminates safely, always
- [ ] **D103 · Model Context Protocol** (2027-02-24, Wed)
    - Build: Expose part of the app as an MCP server
    - Done when: An external MCP client can use your tools
- [ ] **D104 · Async & batch AI workloads** (2027-02-25, Thu)
    - Build: Long-running AI job via Kafka + status polling
    - Done when: Slow inference never blocks a web thread
- [ ] **D105 · Cost, quotas & routing** (2027-02-26, Fri)
    - Build: Token/cost metrics + a cheap-to-strong routing rule
    - Done when: Per-request cost visible in Grafana

---

## PHASE 3 - Langchain4j & Productionising AI


### Week 22 - Langchain4j Core & RAG

- [ ] **D106 · Langchain4j fundamentals** (2027-03-01, Mon)
    - Build: First Langchain4j chat endpoint in the Spring app
    - Done when: Provider swappable via config only
- [ ] **D107 · AiServices, prompts & structured output** (2027-03-02, Tue)
    - Build: Declarative AI service returning typed results
    - Done when: The AI interface is used like any other bean
- [ ] **D108 · Memory & streaming** (2027-03-03, Wed)
    - Build: Multi-user chat with persistent memory and streamed output
    - Done when: Two users never see each other's history
- [ ] **D109 · RAG: ingestion & embedding stores** (2027-03-04, Thu)
    - Build: Ingest the corpus and answer questions over it
    - Done when: Answers come from your documents
- [ ] **D110 · Advanced RAG** (2027-03-05, Fri)
    - Build: Add query rewriting and re-ranking to the pipeline
    - Done when: Measured quality gain over naive retrieval

### Week 23 - Agents, Tools & Comparison

- [ ] **D111 · Tools** (2027-03-08, Mon)
    - Build: Expose the same 3 app operations as Langchain4j tools
    - Done when: The model performs a real action correctly
- [ ] **D112 · Agents & workflows** (2027-03-09, Tue)
    - Build: Build a small multi-step agent workflow
    - Done when: The workflow terminates safely, always
- [ ] **D113 · MCP & Spring Boot integration** (2027-03-10, Wed)
    - Build: Consume an MCP server from a Langchain4j service
    - Done when: The agent calls an external tool through MCP
- [ ] **D114 · Guardrails** (2027-03-11, Thu)
    - Build: Add input/output guardrails to the AI service
    - Done when: Bad input and bad output are both caught
- [ ] **D115 · Spring AI vs Langchain4j** (2027-03-12, Fri)
    - Build: Build the same feature in both and compare
    - Done when: A written decision on which to use when, backed by your own code

### Week 24 - Productionising AI

- [ ] **D116 · Evaluation** (2027-03-15, Mon)
    - Build: Eval harness with 30+ golden questions
    - Done when: Prompt changes are scored, not guessed
- [ ] **D117 · Testing AI features** (2027-03-16, Tue)
    - Build: AI feature tests that run in CI without a provider
    - Done when: CI is green with zero external AI calls
- [ ] **D118 · AI observability & cost** (2027-03-17, Wed)
    - Build: Trace + metrics for every model call
    - Done when: One trace shows retrieval + model + tool spans
- [ ] **D119 · AI security** (2027-03-18, Thu)
    - Build: Injection test suite + fixes
    - Done when: Known injection payloads fail against your app
- [ ] **D120 · Phase 3 consolidation** (2027-03-19, Fri)
    - Build: AI architecture doc + 5-minute demo
    - Done when: You can defend every AI design decision

---

## PHASE 4 - Interview Preparation


### Week 25 - Core Revision

- [ ] **D121 · Java & JVM rapid fire** (2027-03-22, Mon)
    - Build: 100-question self-quiz, written answers
    - Done when: Under 60s per answer, out loud
- [ ] **D122 · Spring internals rapid fire** (2027-03-23, Tue)
    - Build: 100-question self-quiz, written answers
    - Done when: You can explain proxying and its limits cold
- [ ] **D123 · Databases & JPA** (2027-03-24, Wed)
    - Build: 25 SQL problems + 25 JPA/DB questions
    - Done when: You can write and optimise a non-trivial query live
- [ ] **D124 · Kafka & Redis drill** (2027-03-25, Thu)
    - Build: 40 Kafka + Redis questions, answered aloud
    - Done when: Every answer references something you built
- [ ] **D125 · Microservices & distributed systems** (2027-03-26, Fri)
    - Build: Failure-scenario drill: 15 "what breaks and why"
    - Done when: Answers reference your own project

### Week 26 - Mocks, Story & Close

- [ ] **D126 · Low-level design with Spring** (2027-03-29, Mon)
    - Build: Two LLD designs, coded and explained
    - Done when: Both finished within time, with clean abstractions
- [ ] **D127 · AI engineering questions** (2027-03-30, Tue)
    - Build: 30 AI-backend questions, answered aloud
    - Done when: You can defend your AI design choices with numbers
- [ ] **D128 · Project deep dive** (2027-03-31, Wed)
    - Build: 4-minute project pitch + 10 follow-up answers
    - Done when: The pitch is crisp and provokes good questions
- [ ] **D129 · Mock - technical round** (2027-04-01, Thu)
    - Build: Record a 60-minute mock, then self-review
    - Done when: Written list of 5 concrete fixes
- [ ] **D130 · Final - behavioural & close** (2027-04-02, Fri)
    - Build: Ship the final portfolio + write the next plan
    - Done when: Everything public, polished and honest
