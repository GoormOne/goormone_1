package com.profect.delivery.global.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Column(name = "store_name", length = 10)
    private  String storeName;

    @Column(name = "store_description", columnDefinition = "TEXT", nullable = false)
    private String storeDescription;

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

    @Column(name = "open_time")
    private LocalTime openTime;

    @Column(name="close_time")
    private LocalTime closeTime;

    @Column(name = "is_banned", nullable = false)
    private Boolean isBanned = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StoreRegion> storeRegions = new ArrayList<>();

    @CreatedBy
    @Column(name = "created_by", length = 10, nullable = false)
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

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Menu> menus = new ArrayList<>();

    public List<Region> getRegions() {
        return storeRegions.stream()
                .filter(sr -> sr.getDeletedAt() == null) // soft delete 제외
                .map(StoreRegion::getRegion)
                .collect(Collectors.toList());
    }

}