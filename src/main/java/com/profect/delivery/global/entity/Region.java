package com.profect.delivery.global.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "p_regions")
@Getter
@Setter
@NoArgsConstructor
public class Region {

    @Id
    @Column(name = "region_id", nullable = false)
    private UUID regionId;

    @Column(name = "region_1depth_name", length = 50)
    private String region1DepthName;

    @Column(name = "region_2depth_name", length = 50)
    private String region2DepthName;

    @Column(name = "region_3depth_name", length = 50)
    private String region3DepthName;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;


    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL)
    private List<StoreRegion> storeRegions = new ArrayList<>();
}