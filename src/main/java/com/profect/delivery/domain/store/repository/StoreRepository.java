package com.profect.delivery.domain.store.repository;

import com.profect.delivery.domain.store.dto.response.StoreSearchDto;
import com.profect.delivery.global.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StoreRepository extends JpaRepository<Store, UUID> {

    //void deleteByStoreId(String storeId);

    Optional<Store> findByStoreId(UUID storeId);
    List<Store> findByStoreIdIn(List<UUID> storeIds);

    @Query(value = """
        SELECT region_id
        FROM p_regions
        WHERE region_1depth_name = :address1
          AND region_2depth_name = :address2
          AND region_3depth_name = :address3
        """, nativeQuery = true)
    UUID findRegionIdByFullAddress(
            @Param("address1") String address1,
            @Param("address2") String address2,
            @Param("address3") String address3
    );
    // Store 이름으로 조회
    @Query("SELECT s.storeId FROM Store s WHERE s.deletedAt IS NULL AND LOWER(s.storeName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<UUID> findStoreIdsByName(@Param("keyword") String keyword);

    // Menu 이름으로 조회
    @Query("SELECT DISTINCT s.storeId FROM Store s JOIN s.menus m WHERE s.deletedAt IS NULL AND LOWER(m.menuName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<UUID> findStoreIdsByMenu(@Param("keyword") String keyword);

//    @Query("SELECT DISTINCT s FROM Store s " +
//            "LEFT JOIN FETCH s.menus m " +
//            "WHERE s.deletedAt IS NULL AND (" +
//            "LOWER(s.storeName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
//            "OR LOWER(m.menuName) LIKE LOWER(CONCAT('%', :keyword, '%')))")
//    List<Store> searchStoresByKeyword(@Param("keyword") String keyword);
}