package com._daysOfBatman.SpringBootPractice.Orders;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        System.out.println("OrderService created");
        this.orderRepository = orderRepository;
    }

    public List<String> listOrders() {
        return orderRepository.findAll();
    }
}
