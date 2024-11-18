package com.sparta.gourmate.domain.order.dto;

import com.sparta.gourmate.domain.order.entity.Payment;
import com.sparta.gourmate.domain.order.entity.PaymentStatus;
import com.sparta.gourmate.global.common.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PaymentResponseDto extends BaseDto {
    private UUID paymentId;
    private UUID orderId;
    private Integer amount;
    private String pgInfo;
    private PaymentStatus paymentStatus;

    public PaymentResponseDto(Payment payment) {
        this.paymentId = payment.getId();
        this.orderId = payment.getOrder().getId();
        this.amount = payment.getAmount();
        this.pgInfo = payment.getPgInfo();
        this.paymentStatus = payment.getPaymentStatus();
    }
}
