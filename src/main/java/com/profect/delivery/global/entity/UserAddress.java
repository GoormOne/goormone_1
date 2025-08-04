package com.profect.delivery.global.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "p_customer_address") // 클래스명과 테이블명이 다를 경우를 대비해 @Table 어노테이션을 사용하는 것이 좋습니다.
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAddress {
    @Id
    @Column(name = "address_cd", columnDefinition = "uuid")
    private UUID addressCd;

    @Column(name = "user_id", length = 10)
    private String userId;

    @Column(name = "address_name", length = 20)
    private String addressName;

    @Column(name = "address1", length = 50)
    private String address1;

    @Column(name = "address2", length = 50)
    private String address2;

    @Column(name = "zip_cd", length = 6)
    private String zipCd;

    @Column(name = "user_latitude", precision = 10, scale = 6)
    private BigDecimal userLatitude;

    @Column(name = "user_longitude", precision = 10, scale = 6)
    private BigDecimal userLongitude;

    @Column(name = "is_default")
    private Boolean isDefault;

    @Column(name = "created_at",updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

}