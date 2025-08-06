package com.profect.delivery.domain.review.repository;

import com.profect.delivery.global.entity.ReviewSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReviewSummaryRepository extends JpaRepository<ReviewSummary, UUID> {
    // 매장별 가장 최근 요약 1건 조회
    Optional<ReviewSummary> findTopByStoreIdOrderBySummaryRefreshDateDesc(UUID storeId);
}