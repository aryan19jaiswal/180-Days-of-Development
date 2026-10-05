package com._daysOfBatman.SpringBootPractice.Orders;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderRepository {

    private final List<String> orders = List.of(
            "Order-1: Batmobile",
            "Order-2: Grappling Hook",
            "Order-3: Batarangs"
    );

    public List<String> findAll() {
        return orders;
    }
}
