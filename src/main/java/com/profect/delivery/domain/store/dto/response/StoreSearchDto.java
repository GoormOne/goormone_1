package com.profect.delivery.domain.store.dto.response;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreSearchDto {
    private String storeId;
    private String storeName;
    private String description;
    //private String category;
    private BigDecimal rating;
    private Boolean isOpen;
}
