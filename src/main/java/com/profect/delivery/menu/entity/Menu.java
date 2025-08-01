package com.profect.delivery.menu.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name="p_menus")
public class Menu {
    @Id @GeneratedValue
    @Column(name = "menu_id")
    private UUID menuId;

    @Column(nullable = false)
    private UUID storeId;
    private UUID menuCategoryId;
    private String menuName;
    private int menuPrice;
    private String menuDescription;

    @JsonProperty("isPublic")
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
