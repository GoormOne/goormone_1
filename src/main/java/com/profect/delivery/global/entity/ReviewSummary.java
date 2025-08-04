package com.profect.delivery.global.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "p_review_summary")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewSummary {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "batch_id", nullable = false, updatable = false)
    private UUID batchId;

    @Column(name = "store_id", nullable = false)
    private UUID storeId;

    @Column(name = "period_start", nullable = false)
    private LocalDate periodStart;

    @Column(name = "period_end", nullable = false)
    private LocalDate periodEnd;

    @Column(name = "review_cnt", nullable = false)
    private Short reviewCount;

    @Column(name = "summary_text")
    private String summaryText;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by", length = 10)
    private String createdBy;
}
