package com.profect.delivery.domain.menu.dto.response;

import com.profect.delivery.global.entity.Menu;

import java.util.UUID;

public record MenuDetailResponse(
        UUID menuId,
        UUID menuCategoryId,
        String menuName,
        int menuPrice,
        String menuDescription,
        String menuPhotoUrl
) {
    public static MenuDetailResponse from(Menu m) {
        return new MenuDetailResponse(
                m.getMenuId(),
                m.getMenuCategory().getMenuCategoryId(),
                m.getMenuName(),
                m.getMenuPrice(),
                m.getMenuDescription(),
                m.getMenuPhotoUrl()
        );
    }
}