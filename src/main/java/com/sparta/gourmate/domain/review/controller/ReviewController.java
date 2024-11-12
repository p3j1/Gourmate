package com.sparta.gourmate.domain.review.controller;

import com.sparta.gourmate.domain.review.dto.ReviewRequestDto;
import com.sparta.gourmate.domain.review.dto.ReviewResponseDto;
import com.sparta.gourmate.domain.review.service.ReviewService;
import com.sparta.gourmate.domain.user.entity.User;
import com.sparta.gourmate.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 생성
    @PostMapping
    public ResponseEntity<ReviewResponseDto> createReview(@Valid @RequestBody ReviewRequestDto requestDto,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        ReviewResponseDto responseDto = reviewService.createReview(requestDto, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}