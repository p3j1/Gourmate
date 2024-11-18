package com.sparta.gourmate.domain.order.repository;

import com.sparta.gourmate.domain.order.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    Optional<Payment> findByIdAndIsDeletedFalse(UUID paymentId);

    Optional<Payment> findByOrderId(UUID orderId);
}
