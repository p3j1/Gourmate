package com.sparta.gourmate.domain.order;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "p_orders")
public class Order  {  // BaseEntity 상속

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

    @PrePersist
    public void prePersist() {
        this.isDeleted = false;  // 기본값 false
    }
}
