package com.sparta.gourmate.domain.order.repository;

import com.sparta.gourmate.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}
