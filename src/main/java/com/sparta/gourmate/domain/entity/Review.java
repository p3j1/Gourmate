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
    private Long review_id;

    @Column(nullable = false)
    private int rating;

    @Column
    private String review_content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    public Review(int rating, String review_content, User user, Order order, Store store) {
        this.rating = rating;
        this.review_content = review_content;
        this.user = user;
        this.order = order;
        this.store = store;
    }
}
