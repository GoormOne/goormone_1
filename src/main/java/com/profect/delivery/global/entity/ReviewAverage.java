package com.profect.delivery.global.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "p_review_average")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewAverage {

    @Id
    @Column(name = "store_id")
    private UUID storeId; // PK이자 FK

    @Column(name = "count", nullable = false)
    private int count;

    @Column(name = "total", nullable = false)
    private int total;
}
