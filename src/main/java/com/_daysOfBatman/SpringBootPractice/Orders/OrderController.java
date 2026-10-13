package com._daysOfBatman.SpringBootPractice.Orders;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public List<Order> getOrders(@RequestParam(required = false) OrderStatus status) {
        log.info("CONTROLLER getOrders status={}", status);
        return orderService.listOrders(status);
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {
        return orderService.findOrder(id).orElseThrow(() -> notFound(id));
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order request) {
        // The id in the body is ignored: the server owns id assignment.
        Order created = orderService.createOrder(request.item(), request.status());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public Order replaceOrder(@PathVariable Long id, @RequestBody Order request) {
        return orderService.updateOrder(id, request.item(), request.status())
                .orElseThrow(() -> notFound(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        if (!orderService.deleteOrder(id)) {
            throw notFound(id);
        }
        return ResponseEntity.noContent().build();
    }

    private ResponseStatusException notFound(Long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Order " + id + " not found");
    }
}
