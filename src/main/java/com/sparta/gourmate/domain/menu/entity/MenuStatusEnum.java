package com.sparta.gourmate.domain.menu.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MenuStatusEnum {
    AVAILABLE("AVAILABLE"),
    OUT_OF_STOCK("OUT_OF_STOCK"),
    HIDDEN("HIDDEN");

    private final String MenuStatus;

}
