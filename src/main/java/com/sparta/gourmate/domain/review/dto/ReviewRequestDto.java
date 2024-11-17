package com.sparta.gourmate.domain.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class ReviewRequestDto {
    @NotNull(message = "ORDER_NOT_EXISTS")
    private UUID orderId;

    @NotNull(message = "STORE_NOT_EXISTS")
    private UUID storeId;

    @NotNull(message = "REVIEW_RATING_EMPTY")
    @Max(value = 5, message = "REVIEW_RATING_POLICY")
    @Min(value = 0, message = "REVIEW_RATING_POLICY")
    private Integer rating;

    private String content;
}
