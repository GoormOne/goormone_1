package com.profect.delivery.domain.store.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class StoreListDto {
    String storeId;
    String name;
    String category;
    String rating;
    Boolean isOpen;

}
