# D005 — Configuration & profiles

**Build:** Multi-profile config (local/dev/prod)
**Done when:** The app boots correctly under all three profiles
**Topics:** `application.properties` · property precedence · `@ConfigurationProperties` + validation · profiles · env vars · `@Value`

---

## 1. The problem

The same jar has to run on a laptop, in dev, and in prod. The database URL, log level, port, and feature flags differ between them. Rebuilding the app per environment is slow and risky, because the artifact you tested is no longer the artifact you ship.

Two questions follow:
- Where can config come from?
- When two sources disagree, which one wins?

## 2. How we solve it

**Build once, change behaviour from outside the jar.** Three pieces work together.

### 2.1 The `Environment` (ordered property sources)

Spring's `Environment` is an ordered list of property sources, searched top to bottom. The first source that has the key wins.

```text
Environment
 ├─ 1. Command-line args            --server.port=9000
 ├─ 2. SPRING_APPLICATION_JSON      (env var holding JSON)
 ├─ 3. OS environment variables     SERVER_PORT=9000
 ├─ 4. Profile files                application-prod.properties
 ├─ 5. Default file                 application.properties
 └─ 6. Code defaults                field initialisers / @Value("${x:default}")

higher in the list overrides lower
```

This is a simplified ordering. The real list has about 17 entries, so check the official Spring Boot docs before relying on exact positions. The shape is what matters: outer layers override inner ones, so ops can override anything without touching the jar.

### 2.2 Profile-specific `.properties` files

One shared file plus one file per environment:

```text
src/main/resources/
 ├─ application.properties          shared defaults (spring.application.name, etc.)
 ├─ application-local.properties    local overrides
 ├─ application-dev.properties      dev overrides
 └─ application-prod.properties     prod overrides
```

Rules:
- `application-{profile}.properties` loads **on top of** `application.properties` when that profile is active.
- Keys in the profile file override the same keys in the default file. Keys it doesn't mention fall through to the default.
- Example contents: `server.port`, `logging.level.root`, `hello.greeting`, `app.environment-name`.

Activating a profile:

| Method | Example |
|---|---|
| Property | `spring.profiles.active=dev` |
| Command line | `--spring.profiles.active=dev` |
| Environment variable | `SPRING_PROFILES_ACTIVE=dev` |

Activation is itself a property, so the same precedence rules apply to it.

### 2.3 Typed, validated settings

Instead of scattering `@Value` strings, group settings into one class and validate it so a bad deploy fails at startup.

```java
@ConfigurationProperties("app")
@Validated
public class AppProperties {
    @NotBlank private String environmentName;
    @Min(1)   private int timeoutSeconds = 5;
    // getters / setters
}
```

> Bean Validation needs an implementation on the classpath, normally `spring-boot-starter-validation`. This project's `build.gradle.kts` doesn't list it yet, and without it `@NotBlank` and `@Min` do nothing.

### 2.4 Secrets stay out of the files

Passwords and API keys never go in committed `.properties` files. They come from environment variables or a secrets manager (Vault, cloud secret stores, Kubernetes Secrets). Git history is permanent, repo access shouldn't mean prod access, and rotating a secret shouldn't need a rebuild.

## 3. Annotations used

| Annotation | Purpose |
|---|---|
| `@ConfigurationProperties("app")` | Binds all `app.*` properties to the fields of a class. Supports relaxed binding (`environment-name`, `environmentName`, `ENVIRONMENT_NAME`). |
| `@Validated` | Turns on Bean Validation for the properties class, so constraint violations stop startup. |
| `@NotBlank`, `@Min` | Bean Validation constraints on fields (required non-empty string, minimum numeric value). |
| `@EnableConfigurationProperties(AppProperties.class)` | Registers the properties class as a bean (used in an auto-config or `@Configuration`). Alternative: `@ConfigurationPropertiesScan` on the application class. |
| `@Profile("local")` | Registers a bean or `@Configuration` class only when that profile is active. Accepts expressions like `"!prod"`. |
| `@Value("${app.name:default}")` | Injects a single property, with an optional default after the colon. Used sparingly. |

### `@Value` vs `@ConfigurationProperties`

| | `@Value` | `@ConfigurationProperties` |
|---|---|---|
| Shape | One scattered field at a time | A typed object bound as a group |
| Relaxed binding | Limited | Full |
| Validation | None built in | `@Validated` + Bean Validation |
| Nested / list / map | Awkward | Natural |
| Best for | One quick value | Anything that is "settings" |

## 4. Production considerations

- **Fail fast:** validate config at startup so a bad deploy never takes traffic.
- **One artifact, many environments:** the jar is identical everywhere; only the outside changes.
- **Profile sprawl:** prefer a few environment profiles and use properties for fine-grained toggles.
- **Dangerous defaults:** a default that is fine locally (verbose logging, in-memory DB) can hurt in prod if the prod file forgets to override it.
- **Unset profile in prod:** if `SPRING_PROFILES_ACTIVE` is missing, the app silently runs with default config. Decide deliberately what the default should be.
- **Config visibility:** log non-sensitive resolved values at startup, never secrets.

## 5. Interview-ready summary

> Spring Boot externalised configuration builds an `Environment` from ordered property sources (command line, environment variables, profile-specific files, default file, code defaults); higher sources override lower ones. Profiles let the same artifact load extra config files and beans per environment. `@ConfigurationProperties` binds groups of settings to typed, validated objects, so bad config fails at startup rather than at runtime.

## 6. Interview questions with answers

**1. In what order does Spring Boot resolve a property defined in `application.properties`, `application-dev.properties`, an environment variable, and a command-line argument?**

Highest to lowest priority: command-line argument, then environment variable, then `application-dev.properties` (profile-specific), then `application.properties`. The first source containing the key wins, so the command line can override everything without a rebuild.

**2. When would you choose `@Value` over `@ConfigurationProperties`, and why is the latter usually better?**

`@Value` suits a single, simple value, such as one flag in a small class. `@ConfigurationProperties` is usually better for groups of settings: it gives a typed object, relaxed binding, nested/list/map support, and `@Validated` validation, and it keeps all related settings in one place instead of scattered strings that are easy to mistype.

**3. What is relaxed binding? Show three spellings that bind to the same property.**

Spring Boot matches property names flexibly, so different naming styles map to the same field. For a field `environmentName` under prefix `app`: `app.environment-name` (kebab-case, recommended in files), `app.environmentName` (camelCase), and `APP_ENVIRONMENTNAME` (environment variable style: uppercase, underscores).

**4. How do you keep a prod database password out of the repository, and how does the app receive it?**

Keep it out of every committed file. Supply it at runtime through an environment variable, or fetch it from a secrets manager such as Vault, a cloud secret store, or Kubernetes Secrets mounted as env vars or files. The properties file can reference it with a placeholder like `spring.datasource.password=${DB_PASSWORD}`, and the value is injected by the deployment environment.

**5. A prod deploy boots but behaves like local. What do you check first?**

Check which profile is active: whether `SPRING_PROFILES_ACTIVE` (or `--spring.profiles.active`) was actually set in the deployment. Then check the startup log line that prints active profiles, look for a higher-priority override (an environment variable or command-line argument), and confirm the `application-prod.properties` file is packaged in the jar.

**6. What does `@Validated` on a `@ConfigurationProperties` class buy you in production?**

It makes missing or invalid settings fail the application at startup with a clear message naming the property, instead of causing a runtime error on the first request. A bad deploy then never receives traffic, and orchestrators such as Kubernetes keep the old version running.

---

## Notes

<!-- Add your own takeaways, mistakes, and observations from the three profile runs here -->
