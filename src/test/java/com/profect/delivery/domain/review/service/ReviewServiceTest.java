package com.profect.delivery.domain.review.service;

import com.profect.delivery.global.exception.BusinessException;
import com.profect.delivery.domain.review.dto.ReviewResponseDto;
import com.profect.delivery.domain.review.repository.ReviewRepository;
import com.profect.delivery.global.entity.Review;
import com.profect.delivery.global.exception.custom.ReviewErrorCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    void 리뷰가_존재하면_정상적으로_반환된다() {
        // given
        UUID storeId = UUID.randomUUID();
        UUID reviewId = UUID.randomUUID();

        Review review = Review.builder()
                .reviewId(reviewId)
                .storeId(storeId)
                .userId("U0001")
                .rating((short) 4)
                .comment("맛있어요!")
                .createdAt(LocalDateTime.of(2025, 8, 1, 10, 0))
                .isPublic(true)
                .build();

        when(reviewRepository.findByStoreIdAndReviewIdAndIsPublicTrue(storeId, reviewId))
                .thenReturn(Optional.of(review));

        // when
        ReviewResponseDto result = reviewService.getReviewByStoreIdAndReviewId(storeId, reviewId);

        // then
        assertEquals(reviewId, result.getReviewId());
        assertEquals("U0001", result.getUserId());
        assertEquals("맛있어요!", result.getComment());
        assertEquals(4, result.getRating());
        assertEquals("2025-08-01", result.getCreatedAt()); // 날짜 포맷 확인
    }

    @Test
    void 리뷰가_존재하지_않으면_예외를_던진다() {
        // given
        UUID storeId = UUID.randomUUID();
        UUID reviewId = UUID.randomUUID();

        when(reviewRepository.findByStoreIdAndReviewIdAndIsPublicTrue(storeId, reviewId))
                .thenReturn(Optional.empty());

        // when
        BusinessException exception = assertThrows(BusinessException.class,
                () -> reviewService.getReviewByStoreIdAndReviewId(storeId, reviewId));

        // then
        assertEquals(ReviewErrorCode.NOT_FOUND_REVIEW, exception.getDefaultErrorCode());
    }
}
