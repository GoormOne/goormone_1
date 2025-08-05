package com.profect.delivery.domain.review.service;

import com.profect.delivery.global.exception.BusinessException;
import com.profect.delivery.domain.review.dto.ReviewResponseDto;
import com.profect.delivery.domain.review.dto.ReviewListResponseDto;
import com.profect.delivery.domain.review.dto.ReviewRequestDto;
import com.profect.delivery.domain.review.repository.ReviewRepository;
import com.profect.delivery.domain.users.service.UserService;
import com.profect.delivery.global.dto.ErrorResponse;
import com.profect.delivery.global.entity.Review;
import com.profect.delivery.global.entity.ReviewSummary;
import com.profect.delivery.global.entity.User;
import com.profect.delivery.global.exception.custom.AuthErrorCode;
import com.profect.delivery.global.exception.custom.BusinessErrorCode;
import com.profect.delivery.global.exception.custom.ReviewErrorCode;
import com.profect.delivery.global.exception.custom.UserErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional

public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserService userService;

    @Transactional(readOnly = true)
    public ReviewListResponseDto getReviewsByStoreId(UUID storeId) {

        // 리뷰 리스트 조회
        List<Review> reviews = reviewRepository.findAllByStoreIdAndIsPublicTrue(storeId);

        if (reviews == null || reviews.isEmpty()) {
            throw new BusinessException(ReviewErrorCode.NOT_FOUND_REVIEW);

        }


        // 리뷰 생성 날짜
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<ReviewResponseDto> reviewDtos = reviews.stream()
                .map(review -> ReviewResponseDto.builder()
                        .reviewId(review.getReviewId())
                        .userId(review.getUserId())
//                        .userName(username) // 필요시 변경/추가
                        .createdAt(review.getCreatedAt().format(formatter))
                        .rating(review.getRating())
                        .comment(review.getComment())
                        .build())
                .collect(Collectors.toList());


        return ReviewListResponseDto.builder() // 나중에 공통된 부분은 한 줄로 리턴해주는 것이 좋음. 아래처럼 길게 작성하면 누락가능해서 원인 찾기 힘듦, 리스트 형태로 저장해서 만들기(유효성 체크가 쉬워짐)
                .storeId(storeId)
                .totalReviews(reviews.size())
                .reviews(reviewDtos)
                .build();
    }

    // 리뷰 한개 조회
    @Transactional(readOnly = true)
    public ReviewResponseDto getReviewByStoreIdAndReviewId(UUID storeId, UUID reviewId) {
        Review review = reviewRepository.findByStoreIdAndReviewIdAndIsPublicTrue(storeId, reviewId)
                .orElseThrow(() -> new BusinessException(ReviewErrorCode.NOT_FOUND_REVIEW));

        return ReviewResponseDto.builder()
                .reviewId(review.getReviewId())
                .userId(review.getUserId())
                .createdAt(review.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .rating(review.getRating())
                .comment(review.getComment())
                .build();
    }



    public void createReview(UUID storeId, ReviewRequestDto dto) {
        if (dto.getUserId() == null || dto.getUserId().isBlank()) {
            throw new BusinessException(UserErrorCode.NOT_FOUND_USER);
        }

        if (dto.getRating() < 1 || dto.getRating() > 5) {
            throw new BusinessException(BusinessErrorCode.CALCULATION_ERROR);
        }

        Review review = Review.builder()
                .storeId(storeId)
                .userId(dto.getUserId())
                .rating(dto.getRating())
                .comment(dto.getComment())
                .createdAt(LocalDateTime.now())
                .createdBy(dto.getUserId())
                .build();

        reviewRepository.save(review);
    }

    public void deleteReview(UUID storeId, UUID reviewId) {
        Review review = reviewRepository.findByStoreIdAndReviewId(storeId, reviewId)
                .orElseThrow(() -> new BusinessException(ReviewErrorCode.NOT_FOUND_REVIEW));

        review.setIsPublic(false);  // 실제 삭제가 아닌 soft delete
        review.setUpdatedAt(LocalDateTime.now());

        reviewRepository.save(review);
    }


    public void updateReview(UUID storeId, UUID reviewId, ReviewRequestDto dto) {
        Review review = reviewRepository.findByStoreIdAndReviewId(storeId, reviewId)
                .orElseThrow(() -> new BusinessException(ReviewErrorCode.NOT_FOUND_REVIEW));


        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        review.setUpdatedAt(LocalDateTime.now());
        review.setUpdatedBy(dto.getUserId());

        // 권한 있는 사용자만 공개 여부 변경 허용
        if (dto.getIsPublic() != null) {
            if (!isAuthorizedToChangeVisibility(dto.getUserId())) {
                throw new BusinessException(AuthErrorCode.UNAUTHORIZED);
            }
            review.setIsPublic(dto.getIsPublic());
        }



    }
    private boolean isAuthorizedToChangeVisibility (String userId){
        // 예: 관리자 또는 특정 접두어 가진 사용자만 가능
        return userId.equals("admin001") || userId.startsWith("manager_");
    }
}

