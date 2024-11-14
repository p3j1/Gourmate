package com.sparta.gourmate.domain.review.repository;

import com.sparta.gourmate.domain.store.dto.AvgResponseDto;
import com.sparta.gourmate.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    List<Review> findAllByUserId(Long userId);

    Page<Review> findAllByStoreId(UUID storeId, Pageable pageable);

    @Query("select new com.sparta.gourmate.domain.store.dto.AvgResponseDto(r.store.id, avg(r.rating)) from Review r group by r.store.id")
    List<AvgResponseDto> calculateAvg();

    long countByStoreId(UUID id);
}
