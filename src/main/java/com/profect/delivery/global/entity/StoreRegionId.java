package com.profect.delivery.global.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;


@Embeddable
public class StoreRegionId implements Serializable {
    private UUID storeId;
    private UUID regionId;

    public StoreRegionId() {}

    public StoreRegionId(UUID storeId, UUID regionId) {
        this.storeId = storeId;
        this.regionId = regionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StoreRegionId)) return false;
        StoreRegionId that = (StoreRegionId) o;
        return Objects.equals(storeId, that.storeId) &&
                Objects.equals(regionId, that.regionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storeId, regionId);
    }
}