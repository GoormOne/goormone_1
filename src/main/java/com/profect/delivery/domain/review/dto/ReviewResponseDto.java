// 리뷰 1개에 대한 dto
package com.profect.delivery.domain.review.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.UUID;

@Validated
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class ReviewResponseDto {
    private UUID reviewId;
    private String userId;
    private String userName;    // username 추가
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime createdAt; // 2025-07-21 형식
    private short rating;
    private String comment;

    public ReviewResponseDto(UUID reviewId, String userId, String username, String comment,
                             Short rating, LocalDateTime createdAt) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.userName = username;
        this.comment = comment;
        this.rating = rating;
        this.createdAt = createdAt;
    }
}

// private List<String> images = null; // 사진 보류
