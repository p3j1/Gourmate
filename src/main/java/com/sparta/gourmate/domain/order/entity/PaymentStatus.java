package com.sparta.gourmate.domain.order.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum PaymentStatus {
    SUCCESS,
    FAIL,
    REFUND;

    @JsonCreator
    public static PaymentStatus from(String value) {
        for (PaymentStatus status : PaymentStatus.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        return null;
    }
}
