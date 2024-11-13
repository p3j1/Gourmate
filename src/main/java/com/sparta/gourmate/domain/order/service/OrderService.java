package com.sparta.gourmate.domain.order.service;

import com.sparta.gourmate.domain.order.dto.OrderRequestDto;
import com.sparta.gourmate.domain.order.entity.Order;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderService {
    Order createOrder(OrderRequestDto orderRequestDto);
    Optional<Order> getOrderById(UUID orderId);
    List<Order> getAllOrders();
    Order updateOrder(UUID orderId, Order updatedOrder);
    void deleteOrder(UUID orderId); // 기존 삭제 메서드
    Order cancelOrder(UUID orderId);
    Order requestPayment(UUID orderId);
    Order requestRefund(UUID orderId);
    void softDeleteOrder(UUID orderId); // 소프트 딜리트 메서드 추가
}
