package com.sparta.gourmate.domain.order.repository;

import com.sparta.gourmate.domain.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    Optional<Order> findByIdAndIsDeletedFalse(UUID orderId);

    Page<Order> findAllByUserIdAndIsDeletedFalse(Long userId, Pageable pageable);

    Page<Order> findAllByStoreIdAndIsDeletedFalse(UUID storeId, Pageable pageable);
}
