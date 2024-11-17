package com.sparta.gourmate.domain.order.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrderItemRequestDto {

    @NotNull(message = "MENU_NOT_EXISTS")
    private UUID menuId;

    @NotNull(message = "ORDER_ITEM_QUANTITY_EMPTY")
    @Positive(message = "ORDER_ITEM_QUANTITY_INVALID")
    private Integer quantity;

    @NotNull(message = "ORDER_ITEM_PRICE_EMPTY")
    @Positive(message = "ORDER_ITEM_PRICE_INVALID")
    private Integer unitPrice;
}
