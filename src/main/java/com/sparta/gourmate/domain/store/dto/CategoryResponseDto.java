package com.sparta.gourmate.domain.store.dto;

import com.sparta.gourmate.domain.store.entity.Category;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CategoryResponseDto {
    private final UUID id;
    private final String name;

    public CategoryResponseDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }
}
