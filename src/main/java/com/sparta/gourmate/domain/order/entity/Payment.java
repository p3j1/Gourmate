package com.sparta.gourmate.domain.order.entity;

import com.sparta.gourmate.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "p_payments")
public class Payment extends BaseEntity {  // BaseEntity 상속

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private String pgInfo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus = PaymentStatus.SUCCESS;

    public Payment(Order order, int amount, String pgInfo, PaymentStatus paymentStatus) {
        this.order = order;
        this.amount = amount;
        this.pgInfo = pgInfo;
        this.paymentStatus = paymentStatus;
    }
}
