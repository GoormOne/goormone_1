package com.profect.delivery.domain.menu.dto;

import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record UpdateMenuRequest(
        UUID menuCategoryId,
        String menuName,
        @Positive Integer menuPrice,
        String menuDescription,
        Boolean isPublic
) {}