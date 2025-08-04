package com.profect.delivery.domain.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    private String cartItemId;
    private String menuId;
    private Integer quantity;
}
