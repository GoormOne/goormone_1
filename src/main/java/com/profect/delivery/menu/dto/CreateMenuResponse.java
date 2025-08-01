package com.profect.delivery.menu.dto;

import com.profect.delivery.menu.entity.Menu;

import java.util.UUID;

public record CreateMenuResponse(UUID menuId, String menuName) {
//    public static CreateMenuResponse from(Menu menu) {
//        return new CreateMenuResponse(menu.getMenuId(), menu.getMenuName());
//    }
}
