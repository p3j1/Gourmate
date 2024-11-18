package com.sparta.gourmate.domain.order.controller;

import com.sparta.gourmate.domain.order.dto.OrderRequestDto;
import com.sparta.gourmate.domain.order.dto.OrderResponseDto;
import com.sparta.gourmate.domain.order.service.OrderService;
import com.sparta.gourmate.domain.user.entity.User;
import com.sparta.gourmate.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<OrderResponseDto> createOrder(
            @Valid @RequestBody OrderRequestDto orderRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        OrderResponseDto createdOrder = orderService.createOrder(orderRequestDto, user);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    // 주문 상세 조회 (GET)
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrderById(
            @PathVariable UUID orderId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        OrderResponseDto orderResponseDto = orderService.getOrderById(orderId, user);
        return ResponseEntity.ok(orderResponseDto);
    }

    // 주문 목록 조회 (GET) - 페이지네이션 추가
    @GetMapping
    public ResponseEntity<Page<OrderResponseDto>> getAllOrders(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "false") boolean isAsc) {
        User user = userDetails.getUser();
        Page<OrderResponseDto> orders = orderService.getAllOrders(user, page - 1, size, sortBy, isAsc);
        return ResponseEntity.ok(orders);
    }

    // 주문 수정 (PUT)
    @PutMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> updateOrder(
            @PathVariable UUID orderId,
            @Valid @RequestBody OrderRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        OrderResponseDto order = orderService.updateOrder(orderId, requestDto, user);
        return ResponseEntity.ok(order);
    }

    // 주문 삭제 (DELETE)
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(
            @PathVariable UUID orderId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        orderService.deleteOrder(orderId, user);
        return ResponseEntity.noContent().build();
    }

    // 주문 취소 (PUT)
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<OrderResponseDto> cancelOrder(
            @PathVariable UUID orderId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        OrderResponseDto order = orderService.cancelOrder(orderId, user);
        return ResponseEntity.ok(order);
    }
}
