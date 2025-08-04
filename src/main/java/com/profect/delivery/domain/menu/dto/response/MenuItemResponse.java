package com.profect.delivery.domain.menu.dto.response;

import com.profect.delivery.global.entity.Menu;

import java.util.UUID;

public record MenuItemResponse(
        UUID menuId,
        String menuName,
        String photoUrl,
        int menuPrice,
        String menuDescription
) {
    public static MenuItemResponse from(Menu menu) {
        return new MenuItemResponse(
                menu.getMenuId(),
                menu.getMenuName(),
                menu.getMenuPhotoUrl(),
                menu.getMenuPrice(),
                menu.getMenuDescription()
        );
    }
}