package com.profect.delivery.domain.menu.dto.response;

import java.util.List;
import java.util.UUID;

public record MenuCategoryResponse(
        UUID menuCategoryId,
        String menuCategoryName,
        List<MenuItemResponse> menus
) {
}