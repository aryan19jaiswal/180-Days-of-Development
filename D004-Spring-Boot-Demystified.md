# D004 — Spring Boot, demystified

**Build:** Write your own tiny auto-configuration starter
**Done when:** Your starter auto-configures in a fresh app
**Topics:** Starters · `@SpringBootApplication` · auto-configuration · `AutoConfiguration.imports` · conditions · `--debug` report

---

## 1. The core idea

Spring Boot adds almost nothing new to the container. Everything it does is built from mechanisms from D001–D003:

```text
@Configuration classes  +  @Bean methods  +  @Conditional  +  ImportSelector
```

Auto-configuration is a convention for packaging ordinary `@Configuration` classes so that:
1. they are discovered from the classpath instead of from your source tree, and
2. they only activate when it makes sense.

There is no special runtime engine. If you can read a `@Configuration` class, you can read any auto-config.

## 2. Starters: dependencies, not code

A starter such as `spring-boot-starter-webmvc` is essentially a build file. It contains a dependency list and little or no Java. It does two jobs:
- **Dependency curation.** It pulls in a compatible set of libraries (Spring MVC, Jackson, embedded Tomcat, validation, and so on) at tested versions.
- **Triggering.** It puts those libraries on the classpath. Auto-config classes later detect them with `@ConditionalOnClass`.

Adding a starter does not create beans. It changes the classpath, and the classpath changes which conditions pass. The beans are created by the auto-configuration jars that the starter also depends on.

**Naming convention:** `spring-boot-starter-*` is reserved for official ones. Third parties use `*-spring-boot-starter`.

**Common design:** a third-party starter is often two modules, an `autoconfigure` module holding the logic and a thin `starter` module that depends on it plus the library. The D004 build can be a single module.

## 3. `@SpringBootApplication` unpacked

```java
@SpringBootConfiguration      // = @Configuration. Marks this as a source of bean definitions
@EnableAutoConfiguration      // turns on the auto-config import mechanism
@ComponentScan(...)           // scans this package and below
public @interface SpringBootApplication {}
```

Two different discovery systems operate here:

| | Component scanning | Auto-configuration |
|---|---|---|
| Finds | Your classes with `@Component`, `@Service`, etc. | Classes listed in `.imports` files |
| Where from | Package of the main class and below | Every jar on the classpath |
| Mechanism | Scan of a package | Reading a known resource file |
| Conditional by default | No | Yes, nearly always |

A library's `com.acme.lib` package is not under your main class's package, so `@ComponentScan` never sees it. Even if it did, scanning would register everything unconditionally. Listing the class in an `.imports` file lets Boot load it with conditions applied.

## 4. How `@EnableAutoConfiguration` works internally

`@EnableAutoConfiguration` carries `@Import(AutoConfigurationImportSelector.class)`.

### 4.1 `ImportSelector`
A Spring Framework hook. During configuration class processing, it returns the **names of additional `@Configuration` classes** to import. That is how one annotation pulls in hundreds of classes.

### 4.2 `DeferredImportSelector`
`AutoConfigurationImportSelector` is a `DeferredImportSelector`, so its imports are processed **after all of your own configuration classes**.

This ordering is why the system is safe. When Boot's `@ConditionalOnMissingBean(DataSource.class)` is evaluated, your `@Bean DataSource` definition is already registered.

### 4.3 The pipeline

```text
1. ConfigurationClassPostProcessor (a BeanFactoryPostProcessor) starts parsing
   your @SpringBootApplication class
2. It processes your own config + component scan results first
3. Deferred selector runs:
   a. Load candidate class names from every
      META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports
      on the classpath (all jars, merged)
   b. Remove duplicates
   c. Remove exclusions (@SpringBootApplication(exclude=...), spring.autoconfigure.exclude)
   d. Filter early: cheap checks (e.g. "is this class even on the classpath?") so
      hundreds of classes are dropped before any are loaded and parsed
   e. Sort by @AutoConfiguration(before/after) and @AutoConfigureOrder
4. Each surviving class is parsed as a normal @Configuration
5. Conditions on the class and on each @Bean method are evaluated
6. Passing @Bean methods become BeanDefinitions
7. Normal container lifecycle takes over (instantiate, inject, post-process: D003)
```

Step 3d matters for performance. Many auto-configs reference classes that may not exist (for example Kafka types). The early filter checks class presence from metadata without loading the class, so a missing library doesn't cause `NoClassDefFoundError` and startup stays fast.

## 5. The `.imports` file

Location, inside the jar:

```text
META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports
```

Content is one fully-qualified class name per line. Comments start with `#`.

**History:**
- **Boot ≤ 2.6:** auto-config classes were listed in `META-INF/spring.factories` under the key `org.springframework.boot.autoconfigure.EnableAutoConfiguration`.
- **Boot 2.7:** introduced the `.imports` file; both worked.
- **Boot 3.0+:** `spring.factories` is no longer read for auto-configuration. Only `.imports`.

The reason was a dedicated, simpler format for one purpose, since `spring.factories` had become a catch-all for unrelated extension points.

**Boot 4 note:** Boot 4 split the large `spring-boot-autoconfigure` jar into smaller per-technology modules. The mechanism (`.imports` file, conditions, deferred selection) is the same, but package names for some conditions and auto-config classes moved. If an import doesn't resolve, check the actual jars instead of relying on older tutorials.

## 6. `@AutoConfiguration`

```java
@AutoConfiguration   // = @Configuration(proxyBeanMethods = false) + ordering attributes
```

- **`proxyBeanMethods = false`**: normally Spring subclasses `@Configuration` classes with CGLIB so calling one `@Bean` method from another returns the singleton. Auto-configs avoid this: they take dependencies as method parameters instead of calling other `@Bean` methods, so no proxy is needed. Startup is faster and memory use is lower.
- **`before` / `after`** control the order among auto-configs. This matters because conditions like `@ConditionalOnMissingBean` only see beans already registered.

## 7. Conditions: the decision layer

`@Conditional` is a Spring Framework annotation. It takes a `Condition` implementation with one method, `matches(context, metadata)`. Boot supplies the practical ones:

| Annotation | Question it asks |
|---|---|
| `@ConditionalOnClass` / `OnMissingClass` | Is this class on the classpath? |
| `@ConditionalOnBean` / `OnMissingBean` | Is a bean of this type already registered? |
| `@ConditionalOnProperty` | Is this property set (and to this value)? |
| `@ConditionalOnWebApplication` | Servlet, reactive, or none? |
| `@ConditionalOnResource` | Does this file exist? |
| `@ConditionalOnExpression` | Does this SpEL expression hold? |
| `@ConditionalOnSingleCandidate` | Is there exactly one (or one primary) of this type? |

### Two evaluation phases
- **`PARSE_CONFIGURATION`**: evaluated while reading the class. If it fails, the whole class is skipped. Class-presence checks are here.
- **`REGISTER_BEAN`**: evaluated when about to register a specific bean definition. Bean-presence checks belong here, since they depend on what is already registered.

### Gotchas
1. **`@ConditionalOnBean` / `@ConditionalOnMissingBean` depend on registration order.** They only see definitions registered so far. In your own `@Configuration`, ordering isn't guaranteed, so use them **only in auto-configurations**, which are deferred and ordered.
2. **`@ConditionalOnClass` on a `@Bean` method's return type is unsafe.** Reading the method signature can load a missing class. Put it on the whole config class (read via ASM metadata) or use the `name = "..."` string form.
3. **Multiple condition annotations on one element are ANDed.**

### The back-off pattern

```java
@Bean
@ConditionalOnMissingBean
public HelloService helloService(HelloProperties props) { ... }
```

Boot supplies a default, and any bean of that type you define wins. Example: if you define your own `ObjectMapper`, the deferred auto-config evaluates `OnMissingBean` after seeing your definition, so Boot's `ObjectMapper` is never created.

## 8. Configuration properties

`@ConfigurationProperties("hello")` binds `hello.*` keys to a typed object, so config is structured instead of scattered `@Value` strings.

- **Relaxed binding:** `hello.greeting-text`, `hello.greetingText`, and `HELLO_GREETING_TEXT` (environment variable) all bind to the same field.
- **Property sources have precedence** (command line > env > `application.properties` > defaults). D005 covers this properly.
- In an auto-config, use `@EnableConfigurationProperties(HelloProperties.class)` to register the properties class as a bean.

## 9. The `--debug` condition evaluation report

Boot records the outcome of every condition check in a `ConditionEvaluationReport`. With `--debug` (or `debug=true`) it prints:

- **Positive matches:** what applied and which conditions passed.
- **Negative matches:** what didn't apply and the first condition that failed.
- **Exclusions:** what was explicitly excluded.
- **Unconditional classes:** configs with no conditions.

Example negative line (roughly): `DataSourceAutoConfiguration: Did not match: @ConditionalOnClass did not find required class 'javax.sql.DataSource'`.

This is the standard way to debug "why isn't my bean created" and "why is this bean here". The Actuator `conditions` endpoint exposes the same data on a running app.

Run it in this project:

```bash
./gradlew bootRun --args='--debug'
```

## 10. Putting it together: adding a starter

```text
build.gradle.kts: + spring-boot-starter-webmvc
        ↓
Tomcat, Spring MVC, Jackson jars land on classpath
        ↓
Startup: .imports files from all jars are merged into a candidate list
        ↓
Early filter: classes whose @ConditionalOnClass targets are missing are dropped
        ↓
Survivors, ordered, run their remaining conditions
   - DispatcherServletAutoConfiguration: servlet web app? DispatcherServlet on classpath? → yes
   - JacksonAutoConfiguration: ObjectMapper on classpath, no user ObjectMapper? → yes
   - DataSourceAutoConfiguration: no JDBC driver on classpath → class dropped
        ↓
BeanDefinitions registered → standard lifecycle from D003
```

This project has no JDBC or JPA dependency, so database auto-config sits in Negative matches. Adding `spring-boot-starter-data-jpa` changes one line on the classpath and a large set of beans appears.

## 11. Design trade-offs and failure modes

- **Convention vs. transparency.** Auto-config is productive, but beans appear that aren't in your code. `--debug` and the Actuator are the mitigation.
- **Classpath-driven behaviour.** An app can change behaviour because a transitive dependency appeared. A classic source of "works locally, not in prod".
- **Overriding too coarsely.** Defining one bean can disable a whole bundle of defaults (e.g. a custom `SecurityFilterChain` replaces Boot's default security setup). Know what your override replaces.
- **Startup cost.** Hundreds of condition evaluations happen at startup. GraalVM native images do this work at build time (AOT), fixing conditions then.
- **Don't auto-configure what an app can just declare.** For a single app, a plain `@Configuration` is simpler. Starters pay off when the same setup is shared across many apps.

## 12. The build: `hello-spring-boot-starter`

```text
hello-spring-boot-starter/
 ├─ HelloProperties          @ConfigurationProperties("hello")   → greeting text, enabled flag
 ├─ HelloService             plain class, no Spring annotations
 ├─ HelloAutoConfiguration   @AutoConfiguration + conditions
 └─ resources/META-INF/spring/
      org.springframework.boot.autoconfigure.AutoConfiguration.imports
```

Requirements:
- Registers `HelloService` only if the user hasn't defined their own (`@ConditionalOnMissingBean`).
- Can be switched off with `hello.enabled=false` (`@ConditionalOnProperty`).
- Reads the greeting from properties.

**Done when:** a fresh Spring Boot app adds the starter as a dependency, injects `HelloService` without declaring it, and the `--debug` report lists the auto-config under Positive matches.

## 13. Interview-ready summary

> Spring Boot auto-configuration is a set of conditional `@Configuration` classes, listed in `AutoConfiguration.imports` files across the classpath. `@EnableAutoConfiguration` imports them via a `DeferredImportSelector`, so they are processed after the user's own config. Each one is gated by conditions such as class presence, missing beans, and properties, which let it back off when the user defines their own.

### Practice questions
1. Why doesn't `@ComponentScan` pick up a third-party library's configuration?
2. Why must `@ConditionalOnMissingBean` live in auto-configurations rather than your own config?
3. What did `spring.factories` do for auto-config before Boot 3, and what replaced it?
4. Your app throws `Failed to configure a DataSource`. Walk through how you'd diagnose it.
5. Why is `proxyBeanMethods = false` safe for auto-configs?

---

## Notes

<!-- Add your own takeaways, mistakes, and `--debug` observations here -->
