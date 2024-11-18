package com.sparta.gourmate.domain.order.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum OrderType {
    ONLINE,
    OFFLINE;

    @JsonCreator
    public static OrderType from(String value) {
        for (OrderType status : OrderType.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        return null;
    }
}
