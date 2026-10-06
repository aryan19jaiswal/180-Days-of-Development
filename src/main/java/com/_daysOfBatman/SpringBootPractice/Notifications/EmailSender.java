package com._daysOfBatman.SpringBootPractice.Notifications;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class EmailSender implements NotificationSender {

    @Override
    public String send(String message) {
        return "[EMAIL] " + message;
    }
}
