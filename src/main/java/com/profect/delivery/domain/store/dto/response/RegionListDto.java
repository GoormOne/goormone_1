package com.profect.delivery.domain.store.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor

public class RegionListDto {
    private List<RegionDto> regions;
}
