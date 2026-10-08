package com._daysOfBatman.hello;

public class HelloService {

    private final String greeting;

    public HelloService(String greeting) {
        this.greeting = greeting;
    }

    public String greet(String name) {
        return greeting + ", " + name + "!";
    }
}
