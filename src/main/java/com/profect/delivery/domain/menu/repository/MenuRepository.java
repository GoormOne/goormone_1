package com.profect.delivery.domain.menu.repository;

import com.profect.delivery.global.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MenuRepository extends JpaRepository<Menu, UUID> {
    boolean existsByStoreIdAndMenuName(UUID storeId, String menuName);
    boolean existsByStoreIdAndMenuNameAndMenuIdNot(UUID storeId, String menuName, UUID menuId); // 해당 매장에 동일한 이름을 가진 다른 메뉴가 존재하는지
    Optional<Menu> findByStoreIdAndMenuId(UUID storeId, UUID menuId);
    Optional<Menu> findByStoreIdAndMenuName(UUID storeId, String menuName);
}
