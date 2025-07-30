package com.profect.delivery.domain.store.repository;

import com.profect.delivery.global.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StoreRepository extends JpaRepository<Store, UUID> {

    //void deleteByStoreId(String storeId);

    Optional<Store> findByStoreId(UUID storeId);

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
}
