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