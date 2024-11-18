package com.sparta.gourmate.domain.order.entity;

import com.sparta.gourmate.domain.order.dto.OrderRequestDto;
import com.sparta.gourmate.domain.store.entity.Store;
import com.sparta.gourmate.domain.user.entity.User;
import com.sparta.gourmate.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
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

    @Column(nullable = false)
    private String address;

    private String orderRequest;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrderType orderType = OrderType.ONLINE;  // 주문 유형 (ONLINE, OFFLINE)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrderStatus orderStatus = OrderStatus.PENDING;  // 주문 상태 (PENDING, CONFIRMED, CANCELLED)

    @Column(nullable = false)
    private int totalPrice;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItemList = new ArrayList<>();

    @OneToOne(mappedBy = "order")
    private Payment payment;

    public Order(OrderRequestDto requestDto, User user, Store store) {
        this.address = requestDto.getAddress();
        this.orderRequest = requestDto.getOrderRequest();
        this.orderType = requestDto.getOrderType();
        this.totalPrice = requestDto.getTotalPrice();
        this.user = user;
        this.store = store;
    }

    public void update(OrderRequestDto requestDto) {
        this.address = requestDto.getAddress();
        this.orderRequest = requestDto.getOrderRequest();
        this.orderType = requestDto.getOrderType();
        this.totalPrice = requestDto.getTotalPrice();
        this.orderStatus = requestDto.getOrderStatus();
    }
}
