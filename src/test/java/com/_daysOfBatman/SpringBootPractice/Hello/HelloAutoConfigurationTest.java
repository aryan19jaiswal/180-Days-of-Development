package com._daysOfBatman.SpringBootPractice.Hello;

import com._daysOfBatman.hello.HelloAutoConfiguration;
import com._daysOfBatman.hello.HelloService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

class HelloAutoConfigurationTest {

    private final ApplicationContextRunner runner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(HelloAutoConfiguration.class));

    @Test
    void createsServiceByDefault() {
        runner.run(ctx -> assertThat(ctx).hasSingleBean(HelloService.class));
    }

    @Test
    void backsOffWhenDisabled() {
        runner.withPropertyValues("hello.enabled=false")
                .run(ctx -> assertThat(ctx).doesNotHaveBean(HelloService.class));
    }

    @Test
    void backsOffWhenUserDefinesOwnBean() {
        runner.withUserConfiguration(CustomConfig.class).run(ctx -> {
            assertThat(ctx).hasSingleBean(HelloService.class);
            assertThat(ctx.getBean(HelloService.class).greet("A")).isEqualTo("Custom, A!");
        });
    }

    @Configuration(proxyBeanMethods = false)
    static class CustomConfig {
        @Bean
        HelloService helloService() {
            return new HelloService("Custom");
        }
    }
}
