package com.profect.delivery.domain.review.controller;

import com.profect.delivery.domain.review.dto.ReviewDto;
import com.profect.delivery.domain.review.service.ReviewService;
import com.profect.delivery.domain.review.dto.ReviewListDto;
import com.profect.delivery.domain.review.dto.ReviewRequestDto;
import com.profect.delivery.global.DTO.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.profect.delivery.global.DTO.ErrorResponse;
import java.util.UUID;
import java.time.LocalDateTime;


// @Validated: 값 유효성 검증 후에 global dto에서 not null 같은 조건 체크
@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 전체 조회
    @GetMapping("/{storeId}")
    public ResponseEntity<ApiResponse<ReviewListDto>> getReviewsByStore(@PathVariable @Validated UUID storeId) {
        ReviewListDto response = reviewService.getReviewsByStoreId(storeId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // 특정 리뷰 조회
    @GetMapping(value="/{storeId}", params="review_id")
    public ResponseEntity<ApiResponse<ReviewDto>> getSingleReview(
            @PathVariable UUID storeId, @RequestParam("review_id") UUID reviewId) {
        try {
            ReviewDto dto = reviewService.getReviewByStoreIdAndReviewId(storeId, reviewId);
            return ResponseEntity.ok(ApiResponse.success(dto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponse.failure(
                    new ErrorResponse(400, e.getMessage(), "/reviews/" + storeId, LocalDateTime.now().toString())
            ));
        }
    }


    // 리뷰 생성
    @PostMapping("/{storeId}")
    public ResponseEntity<ApiResponse<ReviewDto>> createReview(
            @PathVariable UUID storeId,
            @RequestBody ReviewRequestDto request
    ) {
        ReviewDto reviewDto = reviewService.createReview(storeId, request);
        return ResponseEntity.ok(ApiResponse.success(reviewDto));
    }


    // 리뷰 삭제
    @DeleteMapping("/{storeId}")
    public ResponseEntity<ApiResponse<String>> deleteReview(
            @PathVariable UUID storeId,
            @RequestParam("review_id") UUID reviewId
    ) {
        try {
            reviewService.deleteReview(storeId, reviewId);
            return ResponseEntity.ok(ApiResponse.success("리뷰가 삭제되었습니다."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(ApiResponse.failure(new ErrorResponse(
                            400,
                            e.getMessage(),
                            "/reviews/" + storeId,
                            LocalDateTime.now().toString())));
        }
    }


    // 리뷰 수정
    @PutMapping("/{storeId}")
    public ResponseEntity<ApiResponse<String>> updateReview(
            @PathVariable UUID storeId,
            @RequestParam("review_id") UUID reviewId,
            @RequestBody ReviewRequestDto request
    ) {
        try {
            reviewService.updateReview(storeId, reviewId, request);
            return ResponseEntity.ok(ApiResponse.success("리뷰가 수정되었습니다."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(ApiResponse.failure(
                            new ErrorResponse(
                                    400,
                                    e.getMessage(),
                                    "/reviews/" + storeId,
                                    LocalDateTime.now().toString()
                            )));
        }
    }
}
