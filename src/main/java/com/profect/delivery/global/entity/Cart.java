package com.profect.delivery.global.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Entity
@Table(name = "p_carts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "cartItems")
public class Cart {

    @Id
    @GeneratedValue
    @Column(name = "cart_id", columnDefinition = "UUID")
    private UUID cartId;

    @Column(name = "store_id", nullable = false, columnDefinition = "UUID")
    private UUID storeId;

    @Column(name = "user_id", length = 10, nullable = false)
    private String userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "cart_status", nullable = false)
    private CartStatus cartStatus;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by", length = 10)
    private String updatedBy;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CartItem> cartItems;

    // enum 정의
    public enum CartStatus {
        ACTIVE, ORDERED, ABANDONED
    }

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

}