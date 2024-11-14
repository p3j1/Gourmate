package com.sparta.gourmate.domain.order.controller;

import com.sparta.gourmate.domain.order.dto.OrderRequestDto;
import com.sparta.gourmate.domain.order.dto.OrderResponseDto;
import com.sparta.gourmate.domain.order.entity.Order;
import com.sparta.gourmate.domain.order.service.OrderService;
import com.sparta.gourmate.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    // 주문 생성 (POST)
    @PostMapping
    public ResponseEntity<Order> createOrder(
            @Valid @RequestBody OrderRequestDto orderRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUser().getId();
        Order createdOrder = orderService.createOrder(orderRequestDto, userId);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    // 주문 상세 조회 (GET)
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrderById(
            @PathVariable UUID orderId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUser().getId();
        OrderResponseDto orderResponseDto = orderService.getOrderById(orderId, userId);
        return ResponseEntity.ok(orderResponseDto);
    }

    // 주문 목록 조회 (GET) - 페이지네이션 추가
    @GetMapping
    public ResponseEntity<Page<OrderResponseDto>> getAllOrders(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "false") boolean inAsc) {
        Long userId = userDetails.getUser().getId();
        Sort sort = inAsc ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<OrderResponseDto> orders = orderService.getAllOrders(userId, pageable);
        return ResponseEntity.ok(orders);
    }

    // 주문 수정 (PUT)
    @PutMapping("/{orderId}")
    public ResponseEntity<Order> updateOrder(
            @PathVariable UUID orderId,
            @RequestBody OrderRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUser().getId();
        Order order = orderService.updateOrder(orderId, requestDto, userId);
        return ResponseEntity.ok(order);
    }

    // 주문 삭제 (DELETE)
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(
            @PathVariable UUID orderId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUser().getId();
        orderService.deleteOrder(orderId, userId);
        return ResponseEntity.noContent().build();
    }

    // 주문 취소 (PUT)
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<Order> cancelOrder(
            @PathVariable UUID orderId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUser().getId();
        Order order = orderService.cancelOrder(orderId, userId);
        return ResponseEntity.ok(order);
    }

    // 결제 요청 (POST)
    @PostMapping("/{orderId}/payment")
    public ResponseEntity<Order> requestPayment(
            @PathVariable UUID orderId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUser().getId();
        Order order = orderService.requestPayment(orderId, userId);
        return ResponseEntity.ok(order);
    }

    // 환불 요청 (POST)
    @PostMapping("/{orderId}/refund")
    public ResponseEntity<Order> requestRefund(
            @PathVariable UUID orderId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUser().getId();
        Order order = orderService.requestRefund(orderId, userId);
        return ResponseEntity.ok(order);
    }
}

