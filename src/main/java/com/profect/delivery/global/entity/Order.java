package com.profect.delivery.global.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "p_orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id", columnDefinition = "UUID")
    private UUID orderId;

    @Column(name = "user_id", length = 10, nullable = false)
    private String userId;

    @Column(name = "store_id", columnDefinition = "UUID", nullable = false)
    private UUID storeId;

    @Column(name = "address_cd", columnDefinition = "UUID", nullable = false)
    private UUID addressCd;

    @Column(name = "cart_id", columnDefinition = "UUID", nullable = false)
    private UUID cartId;

    @Column(name = "total_price", nullable = false)
    private int totalPrice;

    @Column(name = "request_message", columnDefinition = "TEXT")
    private String requestMessage;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by", length = 10)
    private String createdBy;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "deleted_by", length = 10)
    private String deletedBy;

    @Column(name = "deleted_rs", length = 100)
    private String deletedRs;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}