package com.sparta.gourmate.domain.user.repository;

import com.sparta.gourmate.domain.user.entity.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
    Page<Address> findAllByUserIdAndIsDeletedFalse(Long userId, Pageable pageable);
}
