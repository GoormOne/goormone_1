package com.profect.delivery.domain.store.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class StoreSearchListDto {
    private List<StoreSearchDto> stores;

}