package com.profect.delivery.menu.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record CreateMenuRequest(
        @NotNull UUID menuCategoryId,
        @NotBlank String menuName,
        @Positive int menuPrice,
        String menuDescription,
        @NotNull Boolean isPublic
) {}
