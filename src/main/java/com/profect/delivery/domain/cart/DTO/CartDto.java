package com.profect.delivery.domain.cart.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    private String cartId;
    private String StoreId;
    private String cartStatus;
    private List<ItemDto> itemDtoList;
}
