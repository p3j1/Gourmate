package com.sparta.gourmate.domain.store.dto;

import com.sparta.gourmate.domain.store.entity.Store;

import java.util.UUID;

public class StoreResponseDto {
    private UUID storeId;
    private Long userId;
    private UUID categoryId;
    private String storeName;
    private String location;
    private float averageRating;


    public StoreResponseDto(Store store) {
        this.storeId = store.getId();
        this.userId = store.getUser().getId();
        this.categoryId = store.getCategory().getId();
        this.storeName = store.getName();
        this.location = store.getLocation();
        this.averageRating = store.getAverageRating();
    }
}
