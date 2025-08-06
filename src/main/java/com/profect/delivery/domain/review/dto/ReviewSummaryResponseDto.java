package com.profect.delivery.domain.review.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReviewSummaryResponseDto {
    private String summaryText;
}