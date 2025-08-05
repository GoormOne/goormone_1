package com.profect.delivery.domain.menu.repository;

import com.profect.delivery.global.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface MenuRepository extends JpaRepository<Menu, UUID> {
    boolean existsByStoreStoreIdAndMenuName(UUID storeId, String menuName);
    boolean existsByStoreStoreIdAndMenuNameAndMenuIdNot(UUID storeId, String menuName, UUID menuId); // 해당 매장에 동일한 이름을 가진 다른 메뉴가 존재하는지
    Optional<Menu> findByStoreStoreIdAndMenuId(UUID storeId, UUID menuId);
    Optional<Menu> findByStoreStoreIdAndMenuName(UUID storeId, String menuName);

    @Query("""
           SELECT m
           FROM   Menu m
           WHERE  m.store.storeId = :storeId
             AND (:categoryId IS NULL
                  OR m.menuCategory.menuCategoryId = :categoryId)
             AND (:publicOnly = FALSE
                  OR m.isPublic = TRUE)
           """)
    Page<Menu> searchMenus(@Param("storeId")  UUID storeId,
                           @Param("categoryId") UUID categoryId,
                           @Param("publicOnly") boolean publicOnly,
                           Pageable pageable);
}
