//// ai 요약 자동 스케줄러 (매주 새벽 2시)
//
//package com.profect.delivery.domain.review.service;
//
//import com.profect.delivery.domain.review.repository.ReviewRepository;
//import com.profect.delivery.domain.review.repository.ReviewSummaryRepository;
//import com.profect.delivery.domain.store.repository.StoreRepository;
//import com.profect.delivery.global.entity.Review;
//import com.profect.delivery.global.entity.ReviewSummary;
//import com.profect.delivery.global.entity.Store;
//import com.profect.delivery.infra.api.GeminiApiService;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.temporal.ChronoUnit;
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class ReviewSummaryScheduler {
//
//    private final ReviewRepository reviewRepository;
//    private final ReviewSummaryRepository reviewSummaryRepository;
//    private final GeminiApiService geminiApiService;
//    private final StoreRepository storeRepository;
//
//    // 매일 새벽 3시에 실행
//    @Scheduled(cron = "0 0 2 * * *")
//    @Transactional
//    public void updateSummaries() {
//        // 1. 리뷰가 20개 이상인 매장 ID 리스트 가져오기
//        List<UUID> storeIds = reviewRepository.findAllStoreIdsWithAtLeast20Reviews();
//
//        for (UUID storeId : storeIds) {
//            Optional<Store> storeOpt = storeRepository.findByStoreId(storeId);
//            if (storeOpt.isEmpty()) continue;
//            Store store = storeOpt.get();
//
//            // 2. 마지막 요약 기록 가져오기
//            ReviewSummary latest = reviewSummaryRepository
//                    .findTopByStoreIdOrderBySummaryRefreshDateDesc(storeId)
//                    .orElse(null);
//
//            LocalDate createdDate = store.getCreatedAt().toLocalDate();
//            LocalDate today = LocalDate.now();
//
//            // 3. 최초 요약 조건: 매장 생성일로부터 일주일이 지난 경우만
//            if (latest == null && ChronoUnit.DAYS.between(createdDate, today) < 7) {
//                continue;
//            }
//
//            // 4. 마지막 요약 이후 3개 이상의 리뷰가 쌓였는지 확인
//            LocalDateTime lastSummaryTime = latest != null
//                    ? latest.getSummaryRefreshDate().atStartOfDay()
//                    : null;
//
//            long newReviewCount = lastSummaryTime == null
//                    ? reviewRepository.countByStoreId(storeId)
//                    : reviewRepository.countByStoreIdAndCreatedAtAfter(storeId, lastSummaryTime);
//
//            if (newReviewCount < 3) continue; // 요약 생성 조건 불충족
//
//            // 5. 최신 리뷰 20개 가져오기
//            List<Review> recentReviews = reviewRepository
//                    .findTop20ByStoreIdAndIsPublicTrueOrderByCreatedAtDesc(storeId);
//
//            List<String> comments = recentReviews.stream()
//                    .map(Review::getComment)
//                    .filter(Objects::nonNull)
//                    .collect(Collectors.toList());
//
//            // 6. Gemini API로 요약 생성
//            String summaryText = geminiApiService.summarizeAsKeywords(comments);
//
//            // 7. p_review_summary 테이블에 저장
//            ReviewSummary summary = ReviewSummary.builder()
//                    .batchId(UUID.randomUUID())
//                    .storeId(storeId)
//                    .summaryText(summaryText)
//                    .summaryRefreshDate(LocalDate.now())
//                    .createdAt(LocalDateTime.now())
//                    .createdBy("scheduler")
//                    .build();
//
//            reviewSummaryRepository.save(summary);
//            log.info("AI 리뷰 요약 저장 완료 - storeId={}, 요약={}", storeId, summaryText);
//        }
//    }
//}
