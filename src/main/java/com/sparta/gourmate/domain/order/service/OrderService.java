package com.sparta.gourmate.domain.order.service;

import com.sparta.gourmate.domain.order.dto.OrderRequestDto;
import com.sparta.gourmate.domain.order.dto.OrderResponseDto;
import com.sparta.gourmate.domain.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface OrderService {

    Order createOrder(OrderRequestDto orderRequestDto, Long userId);

    OrderResponseDto getOrderById(UUID orderId, Long userId);

    Page<OrderResponseDto> getAllOrders(Long userId, Pageable pageable);

    Order updateOrder(UUID orderId, OrderRequestDto requestDto, Long userId);

    void deleteOrder(UUID orderId, Long userId);

    Order cancelOrder(UUID orderId, Long userId);

    Order requestPayment(UUID orderId, Long userId);

    Order requestRefund(UUID orderId, Long userId);
}
