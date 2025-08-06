//// 수동 리뷰 요약 생성 (테스트용)
//package com.profect.delivery.domain.review.controller;
//
//import com.profect.delivery.domain.review.dto.ReviewSummaryResponseDto;
//import com.profect.delivery.domain.review.service.ReviewSummaryService;
//import com.profect.delivery.global.dto.ApiResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/reviews/summary")
//@RequiredArgsConstructor
//public class ReviewSummaryController {
//
//    private final ReviewSummaryService reviewSummaryService;
//
//    // 매장 요약 리뷰 가져오기
//    @GetMapping("/{storeId}")
//    public ResponseEntity<ApiResponse<ReviewSummaryResponseDto>> getSummary(@PathVariable UUID storeId) {
//        ReviewSummaryResponseDto response = reviewSummaryService.getLatestSummaryByStore(storeId);
//        return ResponseEntity.ok(ApiResponse.success(response));
//    }
//}