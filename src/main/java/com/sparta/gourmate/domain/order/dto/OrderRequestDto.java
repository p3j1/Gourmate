package com.sparta.gourmate.domain.order.dto;

import com.sparta.gourmate.domain.order.entity.OrderStatus;
import com.sparta.gourmate.domain.order.entity.OrderType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class OrderRequestDto {

    @NotNull(message = "STORE_NOT_EXISTS")
    private UUID storeId;

    @NotBlank(message = "ORDER_ADDRESS_EMPTY")
    private String address;

    private String orderRequest;

    @NotNull(message = "ORDER_TYPE_INVALID")
    private OrderType orderType;

    @NotNull(message = "ORDER_PRICE_EMPTY")
    @Positive(message = "ORDER_PRICE_INVALID")
    private Integer totalPrice;

    @NotNull(message = "ORDER_STATUS_INVALID")
    private OrderStatus orderStatus = OrderStatus.PENDING;

    @Valid
    @NotEmpty(message = "ORDER_ITEMS_EMPTY")
    private List<OrderItemRequestDto> orderItems;
}
