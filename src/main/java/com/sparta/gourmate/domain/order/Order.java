package com.sparta.gourmate.domain.order;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "p_orders")
public class Order extends BaseEntity {  // BaseEntity 상속

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID orderId;  // 주문 ID, PK

    @Column(nullable = false)
    private Long userId;   // 사용자 ID, FK

    @Column(nullable = false)
    private UUID storeId;  // 가게 ID, FK

    @Column(nullable = false, length = 255)
    private String address;  // 배송지

    @Column(nullable = false, length = 255)
    private String orderRequest;  // 요청 사항

    @Column(nullable = false, length = 20)
    private String orderType;  // 주문 유형 (ONLINE, OFFLINE)

    @Column(nullable = false, length = 20)
    private String orderStatus;  // 주문 상태 (PENDING, CONFIRMED, CANCELLED)

    @Column(nullable = false)
    private int totalPrice;  // 총 가격

    @Column(nullable = false)
    private boolean isDeleted;  // 삭제 여부

    public void prePersist() {
        this.isDeleted = false;  // 기본값 false
    }

    // Getter, Setter
    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public UUID getStoreId() {
        return storeId;
    }

    public void setStoreId(UUID storeId) {
        this.storeId = storeId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrderRequest() {
        return orderRequest;
    }

    public void setOrderRequest(String orderRequest) {
        this.orderRequest = orderRequest;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
