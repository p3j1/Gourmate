package com.sparta.gourmate.domain.order.dto;

import com.sparta.gourmate.domain.order.entity.Order;
import com.sparta.gourmate.domain.order.entity.OrderItem;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrderItemRequestDto {
    private Long menuId;
    private int quantity;
    private int unitPrice;

    public OrderItem toOrderItem(Order order) {
        OrderItem item = new OrderItem();
        item.setOrder(order);
        item.setQuantity(this.quantity);
        item.setUnitPrice(this.unitPrice);
        item.setTotalPrice(this.unitPrice * this.quantity);
        return item;
    }
}
