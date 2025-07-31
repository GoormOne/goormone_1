package com.profect.delivery.domain.review.controller;

import com.profect.delivery.domain.review.service.ReviewService;
import com.profect.delivery.domain.review.dto.ReviewListDto;
import com.profect.delivery.domain.review.dto.ReviewRequestDto;
import com.profect.delivery.global.DTO.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

// @Validated: 값 유효성 검증 후에 global dto에서 not null 같은 조건 체크
@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{storeId}")
    public ResponseEntity<ApiResponse<ReviewListDto>> getReviewsByStore(@PathVariable @Validated UUID storeId) {
        ReviewListDto response = reviewService.getReviewsByStoreId(storeId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }


    @PostMapping("/{storeId}")
    public ResponseEntity<ApiResponse<String>> createReview(
            @PathVariable UUID storeId,
            @RequestBody ReviewRequestDto request
    ) {
        reviewService.createReview(storeId, request);
        return ResponseEntity.ok(ApiResponse.success("리뷰가 성공적으로 등록되었습니다."));


//    @DeleteMapping
//    public ResponseEntity<ApiResponse<String>> deleteReview()


    }

}

// try-catch문으로 변경 (강사님 피드백)
//        try {
//            reviewService.createReview(storeId, request);
//        }catch
//        (Exception e){    // controller에서 미리 예외 처리 -> 사용자 경험성 향상, get mapping 도 마찬가지.
//        }