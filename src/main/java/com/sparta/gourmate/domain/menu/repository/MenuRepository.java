package com.sparta.gourmate.domain.menu.repository;

import com.sparta.gourmate.domain.menu.entity.Menu;
import com.sparta.gourmate.domain.menu.entity.MenuStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MenuRepository extends JpaRepository<Menu, UUID> {
    Page<Menu> findAllByStoreIdAndStatusIn(UUID storeId,List<MenuStatusEnum> statusList, Pageable validatedPageable);
    Page<Menu> findAllByStoreIdAndStatusInAndNameContaining(UUID storeId, List<MenuStatusEnum> statusList, String query, Pageable validatedPageable);

}
