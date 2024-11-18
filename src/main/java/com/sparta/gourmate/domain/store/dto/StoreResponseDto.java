package com.sparta.gourmate.domain.store.dto;

import com.sparta.gourmate.domain.store.entity.Store;
import com.sparta.gourmate.global.common.BaseDto;
import lombok.Getter;

import java.util.UUID;

@Getter
public class StoreResponseDto extends BaseDto {
    private final UUID storeId;
    private final Long userId;
    private final CategoryResponseDto category;
    private final String storeName;
    private final String location;
    private final double averageRating;

    public StoreResponseDto(Store store) {
        super(store);
        this.storeId = store.getId();
        this.userId = store.getUser().getId();
        this.category = new CategoryResponseDto(store.getCategory());
        this.storeName = store.getName();
        this.location = store.getLocation();
        this.averageRating = store.getAverageRating();
    }
}
