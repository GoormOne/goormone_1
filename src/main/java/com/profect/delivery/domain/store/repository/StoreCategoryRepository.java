package com.profect.delivery.domain.store.repository;

import com.profect.delivery.global.entity.StoreCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StoreCategoryRepository extends JpaRepository<StoreCategory, UUID> {
    //Optional<StoreCategory> findByStoreCategory(String storeCategory);
    Optional<StoreCategory> findByStoresCategory(String storeCategory);
}
