package com._daysOfBatman.SpringBootPractice.Notifications;

import org.springframework.stereotype.Component;

@Component
public class SmsSender implements NotificationSender {

    @Override
    public String send(String message) {
        return "[SMS] " + message;
    }
}
