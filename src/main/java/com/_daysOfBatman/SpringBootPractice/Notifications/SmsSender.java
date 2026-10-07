package com._daysOfBatman.SpringBootPractice.Notifications;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class SmsSender implements NotificationSender {

    @PostConstruct
    void warmUp() throws InterruptedException {
        Thread.sleep(200);
    }

    @Override
    public String send(String message) {
        return "[SMS] " + message;
    }
}
