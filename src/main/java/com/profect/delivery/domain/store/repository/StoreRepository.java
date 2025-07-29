package com.profect.delivery.domain.store.repository;

import com.profect.delivery.global.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StoreRepository extends JpaRepository<Store, UUID> {

    //void deleteByStoreId(String storeId);

    Optional<Store> findByStoreId(UUID storeId);



}
