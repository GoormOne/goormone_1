package com.profect.delivery.domain.review.service;

import com.profect.delivery.domain.review.repository.ReviewRepository;
import com.profect.delivery.domain.review.repository.ReviewSummaryRepository;
import com.profect.delivery.global.entity.Review;
import com.profect.delivery.global.entity.ReviewSummary;
import com.profect.delivery.infra.api.GeminiApiService;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.StaleObjectStateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewSummaryService {

    private final ReviewRepository reviewRepository;
    private final ReviewSummaryRepository reviewSummaryRepository;
    private final GeminiApiService geminiApiService;

    @Transactional
    public void generateReviewSummaryIfNeeded(UUID storeId) {
        int maxRetries=3;
        int attempts=0;

        while(attempts<maxRetries) {
            try {
                attempts++;
                // 리뷰 조회 및 요약
                List<Review> recentReviews = reviewRepository.findTop20ByStoreIdAndIsPublicTrueOrderByCreatedAtDesc(storeId);
                List<String> comments = recentReviews.stream()
                        .map(Review::getComment)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());

                String summaryText = geminiApiService.summarizeReviewComments(comments);
                if (summaryText == null || summaryText.isBlank()) return;

                ReviewSummary summary = ReviewSummary.builder()
                        .batchId(UUID.randomUUID())
                        .storeId(storeId)
                        .summaryRefreshDate(LocalDate.now())
                        .summaryText(summaryText)
                        .createdAt(LocalDateTime.now())
                        .createdBy("system")
                        .build();

                reviewSummaryRepository.save(summary);
                log.info("리뷰 요약 저장 완료 - storeId={}, 요약={}", storeId, summaryText);
                return;
            }
            catch (OptimisticLockException | StaleObjectStateException e) {
                log.warn("동시성 충돌 - 재시도 {}/{}: {}", attempts, maxRetries, e.getMessage());
                if (attempts >= maxRetries) {
                    log.error("최대 재시도 초과 - 요약 저장 실패");
                    throw e; // 최종 실패 처리
                }
            }
        }
    }
}





// **************** 복잡한 조건 ******************//
//package com.profect.delivery.domain.review.service;
//
//import com.profect.delivery.domain.review.repository.ReviewRepository;
//import com.profect.delivery.domain.review.repository.ReviewSummaryRepository;
//import com.profect.delivery.domain.store.repository.StoreRepository;
//import com.profect.delivery.global.entity.Review;
//import com.profect.delivery.global.entity.ReviewSummary;
//import com.profect.delivery.global.entity.Store;
//import com.profect.delivery.infra.api.GeminiApiService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.temporal.ChronoUnit;
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class ReviewSummaryService {
//
//    private final ReviewRepository reviewRepository;
//    private final ReviewSummaryRepository reviewSummaryRepository;
//    private final StoreRepository storeRepository;
//    private final GeminiApiService geminiApiService;
//
//    @Transactional
//    public void generateManualSummary(UUID storeId) {
//
//        // 1. 매장 조회
//        Store store = storeRepository.findByStoreId(storeId)
//                .orElseThrow(() -> new IllegalArgumentException("매장을 찾을 수 없습니다."));
//
//        LocalDate createdDate = store.getCreatedAt().toLocalDate();
//        LocalDate today = LocalDate.now();
//
//        // 2. 리뷰 개수 확인 (20개 이상?)
//        long reviewCount = reviewRepository.countByStoreId(storeId);
//        if (reviewCount < 20) {
//            throw new IllegalStateException("리뷰가 20개 이상 누적되어야 요약할 수 있습니다.");
//        }
//
//        // 3. 마지막 요약 확인
//        ReviewSummary latest = reviewSummaryRepository
//                .findTopByStoreIdOrderBySummaryRefreshDateDesc(storeId)
//                .orElse(null);
//
//        // 4. 최초 요약 조건: 7일 이상 경과 여부 확인
//        if (latest == null && ChronoUnit.DAYS.between(createdDate, today) < 7) {
//            throw new IllegalStateException("매장 생성일로부터 7일이 지나야 요약할 수 있습니다.");
//        }
//
//        // 5. 이후 새 리뷰 3개 이상 여부 확인
//        LocalDateTime lastSummaryTime = latest != null
//                ? latest.getSummaryRefreshDate().atStartOfDay()
//                : null;
//
//        long newReviewCount = lastSummaryTime == null
//                ? reviewCount
//                : reviewRepository.countByStoreIdAndCreatedAtAfter(storeId, lastSummaryTime);
//
//        if (latest != null && newReviewCount < 3) {
//            throw new IllegalStateException("이전 요약 이후 3개 이상의 리뷰가 누적되어야 새 요약을 생성합니다.");
//        }
//
//        // 6. 최신 리뷰 20개 가져오기
//        List<Review> recentReviews = reviewRepository
//                .findTop20ByStoreIdAndIsPublicTrueOrderByCreatedAtDesc(storeId);
//
//        List<String> comments = recentReviews.stream()
//                .map(Review::getComment)
//                .filter(Objects::nonNull)
//                .collect(Collectors.toList());
//
//        if (comments.isEmpty()) {
//            throw new IllegalStateException("리뷰에 코멘트가 없습니다.");
//        }
//
//        // 7. Gemini API 호출하여 요약
//        String summaryText = geminiApiService.summarizeAsKeywords(comments);
//
//        // 8. 저장
//        ReviewSummary summary = ReviewSummary.builder()
//                .batchId(UUID.randomUUID())
//                .storeId(storeId)
//                .summaryText(summaryText)
//                .summaryRefreshDate(LocalDate.now())
//                .createdAt(LocalDateTime.now())
//                .createdBy("manual")
//                .build();
//
//        reviewSummaryRepository.save(summary);
//        log.info("리뷰 요약 수동 생성 완료 - storeId={}, summary={}", storeId, summaryText);
//    }
//
//    // summary 조회
//    @Transactional(readOnly = true)
//    public ReviewSummary getLatestSummaryByStore(UUID storeId) {
//        return reviewSummaryRepository
//                .findTopByStoreIdOrderBySummaryRefreshDateDesc(storeId)
//                .orElse(null);
//    }
//}
