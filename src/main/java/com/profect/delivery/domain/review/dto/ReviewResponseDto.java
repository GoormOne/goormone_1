// 리뷰 1개에 대한 dto
package com.profect.delivery.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponseDto {
    private UUID reviewId;
    private String userId;
    private String createdAt; // 2025-07-21 형식
    private int rating;
    private String comment;

}

// private List<String> images = null; // 사진 보류
