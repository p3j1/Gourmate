package com.sparta.gourmate.domain.order.repository;

import com.sparta.gourmate.domain.order.entity.Order;
import com.sparta.gourmate.domain.order.entity.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    List<Order> findByOrderStatus(OrderStatus status);

    List<Order> findByUserIdAndIsDeletedFalse(Long userId);

    @Query("SELECT o FROM Order o WHERE o.isDeleted = false")
    List<Order> findAllActiveOrders();

    @Query("SELECT o FROM Order o WHERE o.id = :orderId AND o.isDeleted = false")
    Optional<Order> findActiveOrderById(UUID orderId);

    List<Order> findAllByIsDeletedFalse();

    Optional<Order> findByIdAndIsDeletedFalse(UUID orderId);

    Page<Order> findAllByUserIdAndIsDeletedFalse(Long userId, Pageable pageable);
}
