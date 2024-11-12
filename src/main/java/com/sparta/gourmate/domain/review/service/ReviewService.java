package com.sparta.gourmate.domain.review.service;

import com.sparta.gourmate.domain.order.entity.Order;
import com.sparta.gourmate.domain.order.repository.OrderRepository;
import com.sparta.gourmate.domain.review.dto.ReviewRequestDto;
import com.sparta.gourmate.domain.review.dto.ReviewResponseDto;
import com.sparta.gourmate.domain.review.entity.Review;
import com.sparta.gourmate.domain.review.repository.ReviewRepository;
import com.sparta.gourmate.domain.store.entity.Store;
import com.sparta.gourmate.domain.store.repository.StoreRepository;
import com.sparta.gourmate.domain.user.entity.User;
import com.sparta.gourmate.domain.user.entity.UserRoleEnum;
import com.sparta.gourmate.global.exception.CustomException;
import com.sparta.gourmate.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;
    private final OrderRepository orderRepository;

    // 리뷰 생성
    public ReviewResponseDto createReview(ReviewRequestDto requestDto, User user) {
        checkRole(user);    // 권한 확인
        Store store = checkStore(requestDto.getStoreId());  // 가게 확인
        Order order = checkOrder(requestDto.getOrderId());  // 주문 확인

        Review review = new Review(requestDto, user, store, order);
        reviewRepository.save(review);
        return new ReviewResponseDto(review);
    }

    // 리뷰 목록 조회
    public List<ReviewResponseDto> getReviewList(User user) {
        checkRole(user);    // 권한 확인

        Long userId = user.getId();
        List<Review> reviewList = reviewRepository.findAllByUserId(userId);   // 내가 작성한 리뷰 조회

        List<ReviewResponseDto> responseDtoList = new ArrayList<>();

        for (Review review : reviewList) {
            responseDtoList.add(new ReviewResponseDto(review));
        }
        return responseDtoList;
    }

    // 리뷰 조회
    public ReviewResponseDto getReview(UUID reviewId) {
        Review review = checkReview(reviewId);  // 리뷰 확인

        return new ReviewResponseDto(review);
    }

    // 리뷰 수정
    public ReviewResponseDto updateReview(UUID reviewId, ReviewRequestDto requestDto, User user) {
        Review review = checkReview(reviewId);  // 리뷰 확인
        checkRole(user);    // 권한 확인
        checkUser(review, user);    // 유저 확인

        review.update(requestDto);

        return new ReviewResponseDto(review);
    }

    // 리뷰 삭제
    public void deleteReview(UUID reviewId, User user) {
        Review review = checkReview(reviewId);  // 리뷰 확인
        checkRole(user);    // 권한 확인
        checkUser(review, user);    // 유저 확인

        reviewRepository.deleteById(reviewId);
    }

    // 유저 확인
    private void checkUser(Review review, User user) {
        Long userId = review.getUser().getId();
        if (!Objects.equals(userId, user.getId())) {
            throw new CustomException(ErrorCode.AUTH_AUTHORIZATION_FAILED);
        }
    }

    // 리뷰 확인
    private Review checkReview(UUID reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));
    }

    // 주문 확인
    private Order checkOrder(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));
    }

    // 가게 확인
    private Store checkStore(UUID storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(ErrorCode.STORE_NOT_FOUND));
    }

    // 권한 확인
    private void checkRole(User user) {
        UserRoleEnum role = user.getRole();

        if (role != UserRoleEnum.CUSTOMER) {
            throw new CustomException(ErrorCode.AUTH_AUTHORIZATION_FAILED);
        }
    }
}