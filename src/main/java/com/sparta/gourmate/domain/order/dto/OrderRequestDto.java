package com.sparta.gourmate.domain.order.dto;

import com.sparta.gourmate.domain.order.entity.Order;
import com.sparta.gourmate.domain.order.entity.OrderItem;
import com.sparta.gourmate.domain.order.entity.OrderStatus;
import com.sparta.gourmate.domain.order.entity.OrderType;
import com.sparta.gourmate.domain.store.entity.Store;
import com.sparta.gourmate.domain.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class OrderRequestDto {
    private Long userId;
    private Long storeId;
    private String address;
    private String orderRequest;
    private OrderType orderType;
    private int totalPrice;
    private List<OrderItemRequestDto> orderItems;

    public Order toOrder(User user, Store store) {
        Order order = new Order();
        order.setAddress(this.address);
        order.setOrderRequest(this.orderRequest);
        order.setOrderType(this.orderType);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setTotalPrice(this.totalPrice);
        return order;
    }

    public List<OrderItem> toOrderItems(Order order) {
        return orderItems.stream()
                .map(dto -> dto.toOrderItem(order))
                .collect(Collectors.toList());
    }

    public Order toOrder() {
        return null;
    }
}