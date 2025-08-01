package com.profect.delivery.domain.store.repository;

import com.profect.delivery.global.entity.ReviewAverage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewAverageRepository extends JpaRepository<ReviewAverage, UUID> {

    @Query("SELECT r.count, r.total FROM ReviewAverage r WHERE r.storeId = :storeId")
    List<Object[]> findReviewStatsByStoreId(@Param("storeId") UUID storeId);}