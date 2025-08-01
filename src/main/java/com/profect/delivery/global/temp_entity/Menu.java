package com.profect.delivery.global.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name="p_menus")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Menu {
    @Id @GeneratedValue
    @Column(name = "menu_id")
    private UUID id;

    @Column(nullable = false)
    private UUID storeId;
    private String menuName;
    private int menuPrice;
    private String menuDescription;
    private boolean isPublic;
}
