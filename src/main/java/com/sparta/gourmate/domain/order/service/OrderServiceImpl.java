package com.sparta.gourmate.domain.order.service;

import com.sparta.gourmate.domain.order.entity.Order;
import com.sparta.gourmate.domain.order.entity.OrderItem;
import com.sparta.gourmate.domain.order.entity.OrderStatus;
import com.sparta.gourmate.domain.order.repository.OrderRepository;
import com.sparta.gourmate.domain.order.repository.OrderItemRepository;
import com.sparta.gourmate.domain.order.dto.OrderRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public Order createOrder(OrderRequestDto orderRequestDto) {
        Order order = orderRequestDto.toOrder();
        orderRepository.save(order);

        List<OrderItem> orderItems = orderRequestDto.toOrderItems(order);
        orderItemRepository.saveAll(orderItems);

        return order;
    }

    @Override
    public Optional<Order> getOrderById(UUID orderId) {
        // 삭제되지 않은 주문만 조회
        return orderRepository.findByIdAndIsDeletedFalse(orderId);
    }

    @Override
    public List<Order> getAllOrders() {
        // 삭제되지 않은 모든 주문 조회
        return orderRepository.findAllByIsDeletedFalse();
    }

    @Override
    public Order updateOrder(UUID orderId, Order updatedOrder) {
        Order existingOrder = orderRepository.findByIdAndIsDeletedFalse(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        existingOrder.setAddress(updatedOrder.getAddress());
        existingOrder.setOrderRequest(updatedOrder.getOrderRequest());
        existingOrder.setOrderType(updatedOrder.getOrderType());
        existingOrder.setOrderStatus(updatedOrder.getOrderStatus());
        existingOrder.setTotalPrice(updatedOrder.getTotalPrice());

        return orderRepository.save(existingOrder);
    }

    @Override
    public void deleteOrder(UUID orderId) {
        Order order = orderRepository.findByIdAndIsDeletedFalse(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        // 소프트 딜리트를 수행하여 isDeleted를 true로 설정
        order.softDelete();
        orderRepository.save(order);
    }

    @Override
    public Order cancelOrder(UUID orderId) {
        Order order = orderRepository.findByIdAndIsDeletedFalse(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        order.setOrderStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }

    @Override
    public Order requestPayment(UUID orderId) {
        Order order = orderRepository.findByIdAndIsDeletedFalse(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        order.setOrderStatus(OrderStatus.CONFIRMED);
        return orderRepository.save(order);
    }

    @Override
    public Order requestRefund(UUID orderId) {
        Order order = orderRepository.findByIdAndIsDeletedFalse(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        order.setOrderStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }

    @Override
    public void softDeleteOrder(UUID orderId) {
        // 소프트 딜리트 로직 구현
        Order order = orderRepository.findByIdAndIsDeletedFalse(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        order.softDelete();  // isDeleted = true
        orderRepository.save(order);  // 변경사항 저장
    }
}
