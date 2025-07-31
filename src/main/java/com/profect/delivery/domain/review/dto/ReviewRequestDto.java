package com.profect.delivery.domain.review.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewRequestDto {
    private String userId;
    private Short rating;
    private String comment;
    private Boolean isPublic;
}
// private String userName; // NOT NULL

