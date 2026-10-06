package com._daysOfBatman.SpringBootPractice.Orders;

import com._daysOfBatman.SpringBootPractice.Notifications.NotificationSender;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final Clock clock;
    private final NotificationSender defaultSender;   // fix 1: @Primary on EmailSender
    private final NotificationSender smsSender;       // fix 2: @Qualifier
    private final List<NotificationSender> allSenders; // fix 3: inject every implementation

    public OrderService(OrderRepository orderRepository,
                        Clock clock,
                        NotificationSender defaultSender,
                        @Qualifier("smsSender") NotificationSender smsSender,
                        List<NotificationSender> allSenders) {
        System.out.println("OrderService created");
        this.orderRepository = orderRepository;
        this.clock = clock;
        this.defaultSender = defaultSender;
        this.smsSender = smsSender;
        this.allSenders = allSenders;
    }

    public List<String> listOrders() {
        return orderRepository.findAll();
    }

    public String notifyDefault(String orderId) {
        return defaultSender.send(orderId + " shipped at " + Instant.now(clock));
    }

    public String notifyBySms(String orderId) {
        return smsSender.send(orderId + " shipped at " + Instant.now(clock));
    }

    public List<String> notifyAll(String orderId) {
        String msg = orderId + " shipped at " + Instant.now(clock);
        return allSenders.stream().map(s -> s.send(msg)).toList();
    }
}
