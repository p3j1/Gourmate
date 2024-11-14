package com.sparta.gourmate.domain.order.dto;

import com.sparta.gourmate.domain.order.entity.Order;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class OrderResponseDto {

    private UUID orderId;           // 주문 ID
    private String orderStatus;     // 주문 상태 (PENDING, CONFIRMED, CANCELLED)
    private String orderType;       // 주문 유형 (ONLINE, OFFLINE)
    private int totalPrice;         // 총 가격
    private String address;         // 주소
    private String orderRequest;    // 주문 요청 사항
    private boolean isDeleted;      // 삭제 여부
    private String deletedAt;       // 삭제 일시

    // 생성자
    public OrderResponseDto(Order order) {
        this.orderId = order.getId();
        this.orderStatus = order.getOrderStatus().toString(); // Enum을 String으로 변환
        this.orderType = order.getOrderType().toString(); // Enum을 String으로 변환
        this.totalPrice = order.getTotalPrice();
        this.address = order.getAddress();
        this.orderRequest = order.getOrderRequest();
        this.isDeleted = order.isDeleted();
        this.deletedAt = order.getDeletedAt() != null ? order.getDeletedAt().toString() : null;


    }
}
