package com.sparta.gourmate.domain.order.service;

import com.sparta.gourmate.domain.order.dto.OrderRequestDto;
import com.sparta.gourmate.domain.order.dto.OrderResponseDto;
import com.sparta.gourmate.domain.order.entity.Order;
import com.sparta.gourmate.domain.order.entity.OrderItem;
import com.sparta.gourmate.domain.order.entity.OrderStatus;
import com.sparta.gourmate.domain.order.repository.OrderRepository;
import com.sparta.gourmate.domain.order.repository.OrderItemRepository;
import com.sparta.gourmate.domain.user.entity.User;
import com.sparta.gourmate.domain.user.repository.UserRepository;
import com.sparta.gourmate.global.exception.CustomException;
import com.sparta.gourmate.global.exception.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.userRepository = userRepository;
    }

    public Order createOrder(OrderRequestDto orderRequestDto, Long userId) {
        Order order = orderRequestDto.toOrder();
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_EXISTS));
        order.setUser(user);
        orderRepository.save(order);

        List<OrderItem> orderItems = orderRequestDto.toOrderItems(order);
        orderItemRepository.saveAll(orderItems);

        return order;
    }

    public OrderResponseDto getOrderById(UUID orderId, Long userId) {
        Order order = orderRepository.findByIdAndIsDeletedFalse(orderId)
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));
        return new OrderResponseDto(order);
    }

    public Page<OrderResponseDto> getAllOrders(Long userId, Pageable pageable) {
        Page<Order> orders = orderRepository.findAllByUserIdAndIsDeletedFalse(userId, pageable);
        return orders.map(OrderResponseDto::new);
    }

    public Order updateOrder(UUID orderId, OrderRequestDto requestDto, Long userId) {
        Order existingOrder = orderRepository.findByIdAndIsDeletedFalse(orderId)
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

        existingOrder.setAddress(requestDto.getAddress());
        existingOrder.setOrderRequest(requestDto.getOrderRequest());
        existingOrder.setOrderType(requestDto.getOrderType());
        existingOrder.setOrderStatus(requestDto.getOrderStatus());
        existingOrder.setTotalPrice(requestDto.getTotalPrice());

        return orderRepository.save(existingOrder);
    }

    public void deleteOrder(UUID orderId, Long userId) {
        Order order = orderRepository.findByIdAndIsDeletedFalse(orderId)
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));
        order.softDelete();
        orderRepository.save(order);
    }

    public Order cancelOrder(UUID orderId, Long userId) {
        Order order = orderRepository.findByIdAndIsDeletedFalse(orderId)
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

        order.setOrderStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }

    public Order requestPayment(UUID orderId, Long userId) {
        Order order = orderRepository.findByIdAndIsDeletedFalse(orderId)
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

        order.setOrderStatus(OrderStatus.CONFIRMED);
        return orderRepository.save(order);
    }

    public Order requestRefund(UUID orderId, Long userId) {
        Order order = orderRepository.findByIdAndIsDeletedFalse(orderId)
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

        order.setOrderStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }
}
