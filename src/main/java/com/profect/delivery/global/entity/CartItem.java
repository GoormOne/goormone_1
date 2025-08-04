package com.profect.delivery.global.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "p_cart_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CartItem {

    public CartItem(UUID menuId, int quantity, Cart cart){
        this.menuId = menuId;
        this.quantity = quantity;
        this.cart = cart;
    }

    @Id
    @GeneratedValue
    @Column(name = "cart_item_id", columnDefinition = "UUID")
    private UUID cartItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false, columnDefinition = "UUID")
    private Cart cart;

    @Column(name = "menu_id", nullable = false, columnDefinition = "UUID")
    private UUID menuId;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by", length = 10)
    private String updatedBy;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    private void validateQuantity() {
        if (quantity < 1 || quantity > 999) {
            throw new IllegalArgumentException("Quantity must be between 1 and 999.");
        }
    }
}