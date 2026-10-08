package com._daysOfBatman.SpringBootPractice.Hello;

import com._daysOfBatman.hello.HelloService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = "hello.greeting=Namaste")
class HelloStarterTest {

    @Autowired
    HelloService helloService;

    @Test
    void helloServiceIsAutoConfiguredWithoutBeingDeclared() {
        assertThat(helloService.greet("Aryan")).isEqualTo("Namaste, Aryan!");
    }
}
