package com.profect.delivery.domain.menu.repository;

import com.profect.delivery.global.entity.Menu;
import com.profect.delivery.global.entity.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MenuCategoryRepository extends JpaRepository<MenuCategory,UUID> {
    Optional<MenuCategory> findByMenuCategoryId(UUID menuCategoryId);
}