package com.sparta.gourmate.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "p_categories")
@NoArgsConstructor
public class Category{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long category_id;

    @Column(nullable = false)
    private String category_name;

    public Category(String category_name) {
        this.category_name = category_name;
    }

}