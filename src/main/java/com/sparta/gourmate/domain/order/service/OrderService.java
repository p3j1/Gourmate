package com.sparta.gourmate.domain.order.service;

import com.sparta.gourmate.domain.order.dto.OrderRequestDto;
import com.sparta.gourmate.domain.order.entity.Order;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderService {
    Order createOrder(@Valid OrderRequestDto orderRequestDto, String userId);

    Optional<Order> getOrderById(UUID orderId);

    List<Order> getAllOrders();

    Order updateOrder(UUID orderId, Order updatedOrder);

    void deleteOrder(UUID orderId); // 기존 삭제 메서드

    Order cancelOrder(UUID orderId);

    Order requestPayment(UUID orderId);

    Order requestRefund(UUID orderId);

    void softDeleteOrder(UUID orderId); // 소프트 딜리트 메서드 추가

    Order updateOrder(UUID orderId, OrderRequestDto requestDto, String userId);

}
