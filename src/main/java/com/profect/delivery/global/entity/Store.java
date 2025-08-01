package com.profect.delivery.global.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "p_stores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Store {

    @Id
    @Column(name = "store_id", columnDefinition = "uuid")
    private UUID storeId;

    @Column(name = "user_id", length = 10)
    private String userId;

    @Column(name = "stores_category_id", columnDefinition = "uuid")
    private UUID storesCategoryId;

    @Column(name = "address1", length = 50)
    private String address1;

    @Column(name = "address2", length = 50)
    private String address2;

    @Column(name = "zip_cd", length = 6)
    private String zipCd;

    @Column(name = "store_phone", length = 15)
    private String storePhone;

    @Column(name = "store_latitude", precision = 10, scale = 6)
    private BigDecimal storeLatitude;

    @Column(name = "store_longitude", precision = 10, scale = 6)
    private BigDecimal storeLongitude;

    @Column(name = "is_banned")
    private Boolean isBanned;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_by", length = 10)
    private String createdBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by", length = 10)
    private String updatedBy;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "deleted_by", length = 10)
    private String deletedBy;

    @Column(name = "deleted_rs", length = 100)
    private String deletedReason;
}