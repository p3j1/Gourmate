package com.sparta.gourmate.domain.order;

import jakarta.persistence.*;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "p_order_items")
public class OrderItem extends BaseEntity {  // BaseEntity 상속 (타임스탬프와 생성/수정자 필드 처리)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID orderItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;  // 주문 (FK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int unitPrice;

    @Column(nullable = false)
    private int totalPrice;

    @Column(nullable = false)
    private boolean isDeleted;

    public void prePersist() {
        this.isDeleted = false;
        this.totalPrice = this.unitPrice * this.quantity;
    }

    // Getter, Setter
    public UUID getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(UUID orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
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
