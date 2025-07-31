package com.profect.delivery.domain.store.repository;


import com.profect.delivery.global.entity.Region;
import com.profect.delivery.global.entity.Store;
import com.profect.delivery.global.entity.StoreRegion;
import com.profect.delivery.global.entity.StoreRegionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreRegionRepository extends JpaRepository<StoreRegion, StoreRegionId> {
    Optional<StoreRegion> findByStoreAndRegionAndDeletedAtIsNull(Store store, Region region);
}