package com.sparta.gourmate.domain.order.controller;

import com.sparta.gourmate.domain.order.dto.OrderRequestDto;
import com.sparta.gourmate.domain.order.entity.Order;
import com.sparta.gourmate.domain.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // 주문 생성 (POST)
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        Order createdOrder = orderService.createOrder(orderRequestDto);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    // 주문 상세 조회 (GET) - 삭제되지 않은 주문만 조회
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable UUID orderId) {
        Order order = orderService.getOrderById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found or has been deleted"));
        return ResponseEntity.ok(order);
    }

    // 모든 주문 조회 (GET) - 삭제되지 않은 주문만 조회
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    // 주문 수정 (PUT)
    @PutMapping("/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable UUID orderId, @RequestBody Order updatedOrder) {
        Order order = orderService.updateOrder(orderId, updatedOrder);
        return ResponseEntity.ok(order);
    }

    // 주문 삭제 (DELETE) - 소프트 딜리트 수행
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    // 주문 취소 (PUT)
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<Order> cancelOrder(@PathVariable UUID orderId) {
        Order order = orderService.cancelOrder(orderId);
        return ResponseEntity.ok(order);
    }

    // 결제 요청 (POST)
    @PostMapping("/{orderId}/payment")
    public ResponseEntity<Order> requestPayment(@PathVariable UUID orderId) {
        Order order = orderService.requestPayment(orderId);
        return ResponseEntity.ok(order);
    }

    // 환불 요청 (POST)
    @PostMapping("/{orderId}/refund")
    public ResponseEntity<Order> requestRefund(@PathVariable UUID orderId) {
        Order order = orderService.requestRefund(orderId);
        return ResponseEntity.ok(order);
    }
}

