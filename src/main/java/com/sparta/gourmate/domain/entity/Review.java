package com.sparta.gourmate.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "p_reviews")
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long reviewId;

    @Column(nullable = false)
    private int rating;

    @Column
    private String reviewContent;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    public Review(int rating, String reviewContent, User user, Order order, Store store) {
        this.rating = rating;
        this.reviewContent = reviewContent;
        this.user = user;
        this.order = order;
        this.store = store;
    }
}
