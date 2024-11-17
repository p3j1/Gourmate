package com.sparta.gourmate.domain.order.dto;

import com.sparta.gourmate.domain.menu.dto.MenuResponseDto;
import com.sparta.gourmate.domain.order.entity.OrderItem;
import com.sparta.gourmate.global.common.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrderItemResponseDto extends BaseDto {
    private UUID orderItemId;
    private MenuResponseDto menu;
    private int quantity;
    private int unitPrice;
    private int totalPrice;

    public OrderItemResponseDto(OrderItem orderitem) {
        super(orderitem);
        this.orderItemId = orderitem.getId();
        this.menu = new MenuResponseDto(orderitem.getMenu());
        this.quantity = orderitem.getQuantity();
        this.unitPrice = orderitem.getUnitPrice();
        this.totalPrice = orderitem.getTotalPrice();
    }
}
