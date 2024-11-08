package com.sparta.gourmate.domain.menu.dto;

import com.sparta.gourmate.domain.menu.entity.Menu;
import com.sparta.gourmate.domain.menu.entity.MenuStatusEnum;
import lombok.Getter;

import java.util.UUID;

@Getter
public class MenuResponseDto {
    private final UUID storeId;
    private final UUID menuId;
    private final String menuName;
    private final String description;
    private final Integer price;
    private final MenuStatusEnum status;

    public MenuResponseDto(Menu menu) {
        this.storeId = menu.getStore().getId();
        this.menuId = menu.getId();
        this.menuName = menu.getName();
        this.description = menu.getDescription();
        this.price = menu.getPrice();
        this.status = menu.getStatus();
    }

}
