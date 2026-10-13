package com._daysOfBatman.SpringBootPractice.Orders;

import com._daysOfBatman.SpringBootPractice.Notifications.NotificationSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Slf4j
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

    public List<Order> listOrders(OrderStatus status) {
        log.info("SERVICE listOrders status={}", status);
        List<Order> all = orderRepository.findAll();
        if (status == null) {
            return all;
        }
        return all.stream().filter(o -> o.status() == status).toList();
    }

    public Optional<Order> findOrder(Long id) {
        return orderRepository.findById(id);
    }

    public Order createOrder(String item, OrderStatus status) {
        return orderRepository.insert(item, status == null ? OrderStatus.CREATED : status);
    }

    public Optional<Order> updateOrder(Long id, String item, OrderStatus status) {
        return orderRepository.replace(id, item, status == null ? OrderStatus.CREATED : status);
    }

    public boolean deleteOrder(Long id) {
        return orderRepository.deleteById(id);
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
