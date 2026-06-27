package com.mahardikapratama.order.controller;

import com.mahardikapratama.order.dto.request.CreateOrderRequest;
import com.mahardikapratama.order.dto.response.OrderResponse;
import com.mahardikapratama.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(
            OrderService orderService) {

        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @Valid @RequestBody
            CreateOrderRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderService.createOrder(request));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {

        return ResponseEntity.ok(
                orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(
            @PathVariable UUID id) {

        return ResponseEntity.ok(
                orderService.getOrderById(id));
    }

    @PatchMapping("/{id}/pay")
    public ResponseEntity<OrderResponse> payOrder(
            @PathVariable UUID id) {

        return ResponseEntity.ok(
                orderService.payOrder(id));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<OrderResponse> cancelOrder(
            @PathVariable UUID id) {

        return ResponseEntity.ok(
                orderService.cancelOrder(id));
    }
}