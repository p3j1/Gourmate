package com.sparta.gourmate.domain.order.entity;

import com.sparta.gourmate.domain.store.entity.Store;
import com.sparta.gourmate.domain.user.entity.User;
import com.sparta.gourmate.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "p_orders")
public class Order extends BaseEntity {  // BaseEntity 상속

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column(nullable = false, length = 255)
    private String address;

    @Column(nullable = false, length = 255)
    private String orderRequest;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrderType orderType;  // 주문 유형 (ONLINE, OFFLINE)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrderStatus orderStatus;  // 주문 상태 (PENDING, CONFIRMED, CANCELLED)

    @Column(nullable = false)
    private int totalPrice;

    @Column(nullable = false)
    private boolean isDeleted = false;

    private LocalDateTime deletedAt;

    public void softDelete() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }

    public void setOrderDate(LocalDateTime now) {
    }

    public Iterable<? extends OrderItem> getOrderItems() {
        return null;
    }
}
