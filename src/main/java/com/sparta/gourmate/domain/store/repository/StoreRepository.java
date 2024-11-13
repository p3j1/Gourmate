package com.sparta.gourmate.domain.store.repository;

import com.sparta.gourmate.domain.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StoreRepository extends JpaRepository<Store, UUID> {
    Page<Store> findByCategoryIdAndNameContaining(UUID categoryId, String query, Pageable pageable);

    Page<Store> findByNameContaining(String query, Pageable pageable);
}
