package com.sparta.gourmate.domain.review.dto;

import com.sparta.gourmate.domain.order.dto.OrderResponseDto;
import com.sparta.gourmate.domain.review.entity.Review;
import com.sparta.gourmate.domain.user.dto.UserResponseDto;
import com.sparta.gourmate.global.common.BaseDto;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ReviewResponseDto extends BaseDto {
    private final UUID reviewId;
    private final int rating;
    private final String content;
    private final UserResponseDto user;
    private final OrderResponseDto order;
    private final UUID storeId;

    public ReviewResponseDto(Review review) {
        super(review);
        this.reviewId = review.getId();
        this.rating = review.getRating();
        this.content = review.getContent();
        this.user = new UserResponseDto(review.getUser());
        this.order = new OrderResponseDto(review.getOrder());
        this.storeId = review.getStore().getId();
    }
}
