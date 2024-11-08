package com.sparta.gourmate.domain.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "p_stores")
@NoArgsConstructor
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long store_id;

    @Column(nullable = false)
    private String store_name;

    @Column(nullable = false)
    private String location;

    @Column
    private int average_rating;

    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Store(String store_name, String location, int average_rating, Category category, User user) {
        this.store_name = store_name;
        this.location = location;
        this.average_rating = average_rating;
        this.category = category;
        this.user = user;
    }
}
