package com.sparta.gourmate.domain.order.dto;

import com.sparta.gourmate.domain.order.entity.Order;
import com.sparta.gourmate.domain.order.entity.OrderStatus;
import com.sparta.gourmate.domain.order.entity.OrderType;
import com.sparta.gourmate.global.common.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class OrderResponseDto extends BaseDto {

    private UUID orderId;           // 주문 ID
    private Long userId;
    private UUID storeId;
    private String address;         // 주소
    private String orderRequest;    // 주문 요청 사항
    private OrderStatus orderStatus;     // 주문 상태 (PENDING, CONFIRMED, CANCELLED)
    private OrderType orderType;       // 주문 유형 (ONLINE, OFFLINE)
    private int totalPrice;         // 총 가격
    private PaymentResponseDto payment;
    private List<OrderItemResponseDto> orderItemList;

    // 생성자
    public OrderResponseDto(Order order) {
        super(order);
        this.orderId = order.getId();
        this.userId = order.getUser().getId();
        this.storeId = order.getStore().getId();
        this.address = order.getAddress();
        this.orderRequest = order.getOrderRequest();
        this.orderStatus = order.getOrderStatus();
        this.orderType = order.getOrderType();
        this.totalPrice = order.getTotalPrice();
        this.payment = new PaymentResponseDto(order.getPayment());
        this.orderItemList = order.getOrderItemList().stream().map(OrderItemResponseDto::new).toList();
    }
}
