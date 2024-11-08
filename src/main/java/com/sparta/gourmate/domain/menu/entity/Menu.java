package com.sparta.gourmate.domain.menu.entity;

import com.sparta.gourmate.domain.menu.dto.MenuRequestDto;
import com.sparta.gourmate.domain.store.entity.Store;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "p_menu")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MenuStatusEnum status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    public Menu(MenuRequestDto requestDto, Store store) {
        this.name = requestDto.getMenuName();
        this.description = requestDto.getDescription();
        this.price = requestDto.getPrice();
        this.status = requestDto.getStatus();
        this.store = store;

    }

}
