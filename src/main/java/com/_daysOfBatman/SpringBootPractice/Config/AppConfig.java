package com._daysOfBatman.SpringBootPractice.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration(proxyBeanMethods = false)
public class AppConfig {

    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }
}
