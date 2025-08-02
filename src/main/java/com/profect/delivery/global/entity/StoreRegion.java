package com.profect.delivery.global.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity

@Table(name = "p_stores_regions")
@EntityListeners(AuditingEntityListener.class)
public class StoreRegion {

    @EmbeddedId
    private StoreRegionId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("storeId")
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("regionId")
    @JoinColumn(name = "region_id")
    private Region region;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public void softDelete() {
        this.deletedAt = LocalDateTime.now();
    }

    public StoreRegion(Store store, Region region) {
        this.store = store;
        this.region = region;
        this.id = new StoreRegionId(store.getStoreId(), region.getRegionId());
        this.createdAt = LocalDateTime.now();
        this.deletedAt = null;
    }
    protected  StoreRegion() {}
}