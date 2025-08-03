package com.profect.delivery.domain.cart.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartInfoDto {
    private String userId;
    private List<CartDto> cartDtoList;

}
