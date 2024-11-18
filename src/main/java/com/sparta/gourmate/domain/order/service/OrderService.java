package com.sparta.gourmate.domain.order.service;

import com.sparta.gourmate.domain.menu.entity.Menu;
import com.sparta.gourmate.domain.menu.repository.MenuRepository;
import com.sparta.gourmate.domain.order.dto.OrderItemRequestDto;
import com.sparta.gourmate.domain.order.dto.OrderRequestDto;
import com.sparta.gourmate.domain.order.dto.OrderResponseDto;
import com.sparta.gourmate.domain.order.entity.*;
import com.sparta.gourmate.domain.order.repository.OrderItemRepository;
import com.sparta.gourmate.domain.order.repository.OrderRepository;
import com.sparta.gourmate.domain.order.repository.PaymentRepository;
import com.sparta.gourmate.domain.store.entity.Store;
import com.sparta.gourmate.domain.store.repository.StoreRepository;
import com.sparta.gourmate.domain.user.entity.User;
import com.sparta.gourmate.global.common.Util;
import com.sparta.gourmate.global.exception.CustomException;
import com.sparta.gourmate.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;
    private final PaymentRepository paymentRepository;

    private static final int CANCEL_MINUTE = 5;

    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto, User user) {
        Store store = findStore(orderRequestDto.getStoreId());
        Order order = new Order(orderRequestDto, user, store);
        orderRepository.save(order);

        // 결제
        Payment payment = requestPayment(order, order.getTotalPrice());
        order.setPayment(payment);

        // order items
        List<OrderItemRequestDto> orderItemDtoList = orderRequestDto.getOrderItems();
        List<Menu> menuList = findMenuList(orderItemDtoList);
        List<OrderItem> orderItems = findOrderItemList(order, menuList, orderItemDtoList);
        orderItemRepository.saveAll(orderItems);
        order.setOrderItemList(orderItems);

        return new OrderResponseDto(order);
    }

    @Transactional(readOnly = true)
    public OrderResponseDto getOrderById(UUID orderId, User user) {
        Order order = findOrder(orderId);
        checkOrderUser(user, order);
        return new OrderResponseDto(order);
    }

    @Transactional(readOnly = true)
    public Page<OrderResponseDto> getAllOrders(User user, int page, int size, String sortBy, boolean isAsc) {
        Pageable pageable = Util.createPageableWithSorting(page, size, sortBy, isAsc);
        Page<Order> orders = orderRepository.findAllByUserIdAndIsDeletedFalse(user.getId(), pageable);
        return orders.map(OrderResponseDto::new);
    }

    public OrderResponseDto updateOrder(UUID orderId, OrderRequestDto requestDto, User user) {
        Order order = findOrder(orderId);
        checkOrderUser(user, order);

        order.update(requestDto);
        return new OrderResponseDto(order);
    }

    public void deleteOrder(UUID orderId, User user) {
        Order order = findOrder(orderId);
        checkOrderUser(user, order);

        order.delete(user.getId());
    }

    public OrderResponseDto cancelOrder(UUID orderId, User user) {
        Order order = findOrder(orderId);
        checkOrderUser(user, order);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime cancelAvailable = order.getCreatedAt().plusMinutes(CANCEL_MINUTE);
        if (now.isAfter(cancelAvailable)) {
            throw new CustomException(ErrorCode.ORDER_CANCEL_TIME_EXCEEDED);
        }

        order.setOrderStatus(OrderStatus.CANCELLED);

        Payment payment = requestRefund(order);

        order.setPayment(payment);
        return new OrderResponseDto(order);
    }

    private Order findOrder(UUID orderId) {
        return orderRepository.findByIdAndIsDeletedFalse(orderId)
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));
    }

    private Store findStore(UUID storeId) {
        return storeRepository.findByIdAndIsDeletedFalse(storeId)
                .orElseThrow(() -> new CustomException(ErrorCode.STORE_NOT_FOUND));
    }

    private List<Menu> findMenuList(List<OrderItemRequestDto> orderItemDtoList) {
        List<UUID> menuIdList = orderItemDtoList.stream().map(OrderItemRequestDto::getMenuId).toList();
        return menuRepository.findAllById(menuIdList);
    }

    private List<OrderItem> findOrderItemList(Order order, List<Menu> menuList, List<OrderItemRequestDto> orderItemDtoList) {
        return orderItemDtoList
                .stream()
                .map(dto -> {
                    for (Menu menu : menuList) {
                        if (menu.getId().equals(dto.getMenuId())) {
                            return new OrderItem(dto, order, menu);
                        }
                    }
                    throw new CustomException(ErrorCode.MENU_NOT_FOUND);
                })
                .toList();
    }

    private void checkOrderUser(User user, Order order) {
        if (!Objects.equals(order.getUser().getId(), user.getId())) {
            throw new CustomException(ErrorCode.USER_NOT_SAME);
        }
    }

    private Payment requestPayment(Order order, int amount) {
        // pg 사에 결제 요청 -> 결과값(pgInfo) 받아서 저장
        String pgInfo = order.getUser().getUsername() + "-pg-info";
        PaymentStatus status = PaymentStatus.SUCCESS;

        Payment payment = new Payment(order, amount, pgInfo, status);
        paymentRepository.save(payment);

        return payment;
    }

    private Payment requestRefund(Order order) {
        Payment payment = paymentRepository.findByOrderId(order.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.PAYMENT_NOT_FOUND));
        payment.setPaymentStatus(PaymentStatus.REFUND);

        return payment;
    }

}
