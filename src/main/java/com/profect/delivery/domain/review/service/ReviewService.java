package com.profect.delivery.domain.review.service;

import com.profect.delivery.domain.review.dto.ReviewDto;
import com.profect.delivery.domain.review.dto.ReviewListDto;
import com.profect.delivery.domain.review.dto.ReviewRequestDto;
import com.profect.delivery.domain.review.repository.ReviewAverageRepository;
import com.profect.delivery.domain.review.repository.ReviewRepository;
import com.profect.delivery.global.entity.Review;
import com.profect.delivery.global.entity.ReviewAverage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewAverageRepository reviewAverageRepository; // 평균 평점

    public ReviewListDto getReviewsByStoreId(UUID storeId) {

        // 리뷰 리스트 조회
        List<Review> reviews = reviewRepository.findReviewByStoreId(storeId);
        // 리뷰 생성 날짜
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 리뷰들 평균 평점
        ReviewAverage avg = reviewAverageRepository.findByStoreId(storeId)
                .orElse(new ReviewAverage(storeId, 0, 0));

        double averageRating = avg.getCount() == 0
                ? 0.0
                : Math.round((avg.getTotal() / (double) avg.getCount()) * 10) / 10.0;

        // 리뷰 DTO 변환
        List<ReviewDto> reviewDtos = reviews.stream()
                .map(review -> ReviewDto.builder()
                        .reviewId(review.getReviewId())
                        .userId(review.getUserId())
                        .createdAt(review.getCreatedAt().format(formatter))
                        .rating(review.getRating())
                        .comment(review.getComment())
                        .build())
                .collect(Collectors.toList());

        return ReviewListDto.builder() // 나중에 공통된 부분은 한 줄로 리턴해주는 것이 좋음. 아래처럼 길게 작성하면 누락가능해서 원인 찾기 힘듦, 리스트 형태로 저장해서 만들기(유효성 체크가 쉬워짐)
                .storeId(storeId)
                // storeName 추가해야함
                .averageRating(averageRating)
                .totalReviews(reviews.size())
                .reviews(reviewDtos)
                .build();
    }

    public void createReview(UUID storeId, ReviewRequestDto dto) {
        // storeName은 기존 리뷰에서 참조하거나 기본값 사용

        Review review = Review.builder()
//                .reviewId(UUID.randomUUID())  // 이거 만들어야하나?
                .storeId(storeId)
                //.storeName(storeName)
                .userId(dto.getUserId())
                .rating(dto.getRating())
                .comment(dto.getComment())
                .createdAt(LocalDateTime.now())
                .createdBy(dto.getUserId())
//                .isPublic(true)
                .build();

        reviewRepository.save(review);

        // 평균 평점 갱신
        reviewAverageRepository.findByStoreId(storeId).ifPresentOrElse(
                avg -> {
                    avg.setCount(avg.getCount() + 1);
                    avg.setTotal(avg.getTotal() + dto.getRating());
                    reviewAverageRepository.save(avg);
                },
                () -> {
                    ReviewAverage newAvg = new ReviewAverage(storeId, 1, dto.getRating());
                    reviewAverageRepository.save(newAvg);
                }
        );
    }


    public void deleteReview(UUID storeId, UUID reviewId) {
        boolean exists = reviewRepository.existsByStoreIdAndReviewId(storeId, reviewId);
        if (!exists) {
            throw new IllegalArgumentException("리뷰를 찾을 수 없습니다.");
        }
        reviewRepository.deleteByStoreIdAndReviewId(storeId, reviewId);
    }

}
