package com._daysOfBatman.SpringBootPractice.Orders;

public record Order(Long id, String item, OrderStatus status) {
}
