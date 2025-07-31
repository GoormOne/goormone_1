package com.profect.delivery.global.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "p_reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "review_id", nullable = false, updatable = false)
    private UUID reviewId;

    @Column(name = "store_id", nullable = false)
    private UUID storeId; // FK로 연결 예정

    @Column(name = "user_id", nullable = false, length = 10)
    private String userId; // FK로 연결 예정

//    @Column(name = "store_name", nullable = false, length = 30)
//    private String storeName;

    @Column(name = "rating", nullable = false)
    private Short rating; // SMALLINT

    @Column(name = "comment")
    private String comment;

    @Builder.Default
    @Column(name = "is_public", nullable = false)
    private Boolean isPublic = true;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by", length = 10)
    private String createdBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by", length = 10)
    private String updatedBy;
}
