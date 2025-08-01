package com.profect.delivery.global.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;
@Entity
@Table(name = "p_menus")
@IdClass(MenuId.class)
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Setter
public class Menu {
    @Id @GeneratedValue
    @Column(name = "menu_id", nullable = false)
    private UUID menuId;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_category_id", nullable = false)
    private MenuCategory menuCategory;

    @Column(name = "menu_name", length = 20, nullable = false)
    private String menuName;

    @Column(name = "menu_price", nullable = false)
    private int menuPrice;

    @Column(name = "menu_description", columnDefinition = "TEXT", nullable = false)
    private String menuDescription;

    @Column(name = "is_public", nullable = false)
    private boolean isPublic;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false, length = 10)
    private String createdBy;

    private LocalDateTime updatedAt;
    @Column(length = 10)
    private String updatedBy;

    @Column private LocalDateTime deletedAt;
    @Column(length = 10)
    private String deletedBy;
    private String deletedRs;

    public void changeName(String name)               { this.menuName = name; }
    public void changePrice(Integer price)            { this.menuPrice = price; }
    public void changeCategory(UUID categoryId)       { this.menuCategoryId = categoryId; }
    public void changeDescription(String description) { this.menuDescription = description; }
    public void changeIsPublic(Boolean isPublic)      { this.isPublic = isPublic; }

    public void auditUpdate(String username) {
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = username;
    }
    public void auditDelete(String username) {
        this.deletedAt = LocalDateTime.now();
        this.deletedBy = username;
    }
}
