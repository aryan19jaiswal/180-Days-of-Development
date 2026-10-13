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
        public List<Order> findAll() {
            return List.of(new Order(1L, "Test", OrderStatus.CREATED),
                    new Order(2L, "Shipped Test", OrderStatus.SHIPPED));
        }
    };

    private final OrderService service =
            new OrderService(fakeRepo, fixedClock, fakeSender, fakeSender, List.of(fakeSender));

    @Test
    void listOrdersWithoutFilterReturnsAll() {
        assertEquals(2, service.listOrders(null).size());
    }

    @Test
    void listOrdersFiltersByStatus() {
        assertEquals(List.of(new Order(2L, "Shipped Test", OrderStatus.SHIPPED)),
                service.listOrders(OrderStatus.SHIPPED));
    }

    @Test
    void createDefaultsStatusToCreated() {
        OrderService real = new OrderService(new OrderRepository(), fixedClock, fakeSender, fakeSender, List.of(fakeSender));
        assertEquals(OrderStatus.CREATED, real.createOrder("Cape", null).status());
    }

    @Test
    void notifyDefaultUsesInjectedClockAndSender() {
        assertEquals("[FAKE] Order-1 shipped at 2026-10-07T00:00:00Z", service.notifyDefault("Order-1"));
    }
}
