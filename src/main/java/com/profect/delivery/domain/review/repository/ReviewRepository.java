package com.profect.delivery.domain.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.profect.delivery.global.entity.Review;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> { //<이 repo가 다룰 엔티티 클래스, pk의 타입>
    List<Review> findReviewByStoreId(UUID storeId); // 해당 매장에 달린 모든 리뷰 가져옴

    // 특정 리뷰 존재 여부
    boolean existsByStoreIdAndReviewId(UUID storeId, UUID reviewId);
    // 리뷰 삭제
    void deleteByStoreIdAndReviewId(UUID storeId, UUID reviewId);
}

// 노출 건수는 나중에 추가하기