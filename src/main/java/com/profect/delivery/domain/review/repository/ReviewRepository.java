package com.profect.delivery.domain.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.profect.delivery.global.entity.Review;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> { //<이 repo가 다룰 엔티티 클래스, pk의 타입>

    // 특정 매장의 리뷰 전체 조회
    List<Review> findReviewByStoreId(UUID storeId); // 해당 매장에 달린 모든 리뷰 가져옴

    // 특정 리뷰 존재 여부
    boolean existsByStoreIdAndReviewId(UUID storeId, UUID reviewId);
//    // 리뷰 삭제
//    void deleteByStoreIdAndReviewId(UUID storeId, UUID reviewId);

    // 특정 리뷰 조회, 리뷰 수정
    Optional<Review> findByStoreIdAndReviewId(UUID storeId, UUID reviewId);

    // soft delete용

    // 공개된 리뷰만 가져오기 (리스트 조회에서 사용)
    List<Review> findAllByStoreIdAndIsPublicTrue(UUID storeId);

    // 리뷰 상세 조회 시, 공개된 리뷰만 필터링
    Optional<Review> findByStoreIdAndReviewIdAndIsPublicTrue(UUID storeId, UUID reviewId);

}

// 노출 건수는 나중에 추가하기