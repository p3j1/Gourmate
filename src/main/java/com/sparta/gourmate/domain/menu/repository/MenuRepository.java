package com.sparta.gourmate.domain.menu.repository;

import com.sparta.gourmate.domain.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MenuRepository extends JpaRepository<Menu, UUID> {

}
