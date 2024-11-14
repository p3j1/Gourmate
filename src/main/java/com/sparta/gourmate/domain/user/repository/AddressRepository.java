package com.sparta.gourmate.domain.user.repository;

import com.sparta.gourmate.domain.user.entity.Address;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
    Optional<Address> findById(Long addressId);

    List<Address> findAllByUserIdAndIsDeletedFalse(Long userId, Pageable pageable);
}
