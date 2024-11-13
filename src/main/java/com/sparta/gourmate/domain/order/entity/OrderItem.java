package com.sparta.gourmate.domain.order.entity;

import com.sparta.gourmate.domain.menu.entity.Menu;
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
@Table(name = "p_order_items")
public class OrderItem extends BaseEntity {  // BaseEntity 상속 (타임스탬프와 생성/수정자 필드 처리)

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int unitPrice;

    @Column(nullable = false)
    private int totalPrice;

    @PrePersist
    public void prePersist() {
        this.totalPrice = this.unitPrice * this.quantity;
    }
}
