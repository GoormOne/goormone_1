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

    @Enumerated(EnumType.STRING)
    @Column(name = "stores_category", nullable = false)
    private CategoryType storesCategory;

    public enum CategoryType {
        KOREAN, CHINESE, JAPANESE, WESTERN, SNACK, CAFE, ETC
    }
}