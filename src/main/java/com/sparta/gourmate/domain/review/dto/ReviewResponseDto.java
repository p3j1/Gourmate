package com.sparta.gourmate.domain.review.dto;

import com.sparta.gourmate.domain.review.entity.Review;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ReviewResponseDto {
    private final UUID reviewId;
    private final UUID orderId;
    private final UUID storeId;
    private final int rating;
    private final String content;


    public ReviewResponseDto(Review review) {
        this.reviewId = review.getId();
        this.orderId = review.getOrder().getId();
        this.storeId = review.getStore().getId();
        this.rating = review.getRating();
        this.content = review.getContent();
    }
}
