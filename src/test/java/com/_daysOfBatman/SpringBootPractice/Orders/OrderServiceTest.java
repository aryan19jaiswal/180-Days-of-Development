package com._daysOfBatman.SpringBootPractice.Orders;

import com._daysOfBatman.SpringBootPractice.Notifications.NotificationSender;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderServiceTest {

    private final Clock fixedClock = Clock.fixed(Instant.parse("2026-10-07T00:00:00Z"), ZoneOffset.UTC);
    private final NotificationSender fakeSender = msg -> "[FAKE] " + msg;

    // No Spring: a hand-written fake repository, passed straight to the constructor
    private final OrderRepository fakeRepo = new OrderRepository() {
        @Override
        public List<String> findAll() {
            return List.of("Order-X: Test");
        }
    };

    private final OrderService service =
            new OrderService(fakeRepo, fixedClock, fakeSender, fakeSender, List.of(fakeSender));

    @Test
    void listOrdersDelegatesToRepository() {
        assertEquals(List.of("Order-X: Test"), service.listOrders());
    }

    @Test
    void notifyDefaultUsesInjectedClockAndSender() {
        assertEquals("[FAKE] Order-1 shipped at 2026-10-07T00:00:00Z", service.notifyDefault("Order-1"));
    }
}
