package com.profect.delivery.domain.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.profect.delivery.global.entity.ReviewAverage;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReviewAverageRepository extends JpaRepository<ReviewAverage, UUID>{
    Optional<ReviewAverage> findByStoreId(UUID storeId);  // 평점 없을 수도 있기때문에
}
