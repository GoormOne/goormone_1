package com.profect.delivery.domain.menu.dto.response;

import java.util.UUID;

public record CreateMenuResponse(UUID menuId, String menuName) {
//    public static CreateMenuResponse from(Menu menu) {
//        return new CreateMenuResponse(menu.getMenuId(), menu.getMenuName());
//    }
}