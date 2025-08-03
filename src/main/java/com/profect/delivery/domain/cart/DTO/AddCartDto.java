package com.profect.delivery.domain.cart.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddCartDto {
    @JsonProperty("menu_id")
    private String menuId;
    private String quantity;
}
