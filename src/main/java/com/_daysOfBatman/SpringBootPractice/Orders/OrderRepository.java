package com._daysOfBatman.SpringBootPractice.Orders;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class OrderRepository {

    // Tomcat serves requests on many worker threads, so the store must be thread-safe.
    // Sorted map keeps list output in id order.
    private final ConcurrentNavigableMap<Long, Order> orders = new ConcurrentSkipListMap<>();
    private final AtomicLong sequence = new AtomicLong();

    public OrderRepository() {
        insert("Batmobile", OrderStatus.CREATED);
        insert("Grappling Hook", OrderStatus.SHIPPED);
        insert("Batarangs", OrderStatus.CREATED);
    }

    public List<Order> findAll() {
        return List.copyOf(orders.values());
    }

    public Optional<Order> findById(Long id) {
        return Optional.ofNullable(orders.get(id));
    }

    public Order insert(String item, OrderStatus status) {
        Order order = new Order(sequence.incrementAndGet(), item, status);
        orders.put(order.id(), order);
        return order;
    }

    // Atomic: replaces only if the id exists, so a concurrent delete cannot be "resurrected".
    public Optional<Order> replace(Long id, String item, OrderStatus status) {
        return Optional.ofNullable(
                orders.computeIfPresent(id, (key, existing) -> new Order(key, item, status)));
    }

    public boolean deleteById(Long id) {
        return orders.remove(id) != null;
    }
}
