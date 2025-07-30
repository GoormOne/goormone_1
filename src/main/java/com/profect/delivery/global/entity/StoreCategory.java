package com.profect.delivery.global.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "p_stores_category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreCategory {

    @Id
    @Column(name = "stores_category_id", nullable = false)
    private UUID storesCategoryId;


    @Column(name = "stores_category", nullable = false)
    private String storesCategory;


}