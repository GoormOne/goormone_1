// 리뷰 목록 전체에 대한 응답 // 완료
package com.profect.delivery.domain.review.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

  // lombok이 제공하는 어노테이션, 클래스에 필요한 모든 필수 메소드 자동 생성해줌
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReviewListDto {
    private UUID storeId;
//    private String storeName;
    private double averageRating;
    private int totalReviews;
    private List<ReviewDto> reviews;    // reviews 라는 리뷰 객체들의 목록을 담는 필드, ReviewDto 객체를 여러개 담는 리스트

}