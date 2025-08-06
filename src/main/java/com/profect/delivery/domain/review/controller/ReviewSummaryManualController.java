package com.profect.delivery.domain.review.controller;

import com.profect.delivery.domain.review.service.ReviewSummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/review-summaries")
@RequiredArgsConstructor
public class ReviewSummaryManualController {

    private final ReviewSummaryService reviewSummaryService;

    @PostMapping("/{storeId}/generate")
    public ResponseEntity<String> generateSummary(@PathVariable UUID storeId) {
        reviewSummaryService.generateReviewSummaryIfNeeded(storeId);
        return ResponseEntity.ok("요약 요청 완료");
    }
}

