package com.profect.delivery.domain.menu.dto.response;

import java.util.List;

public record MenuCategoriesResponse(
        List<MenuCategoryResponse> categories,
        long totalElements,
        int totalPages
) {
    public MenuCategoriesResponse(List<MenuCategoryResponse> categories) {
        this(categories, 0L, 0);
    }
}