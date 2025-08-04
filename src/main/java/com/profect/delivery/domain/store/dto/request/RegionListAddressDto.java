package com.profect.delivery.domain.store.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter


public class RegionListAddressDto {

    @JsonProperty("regionlist")
    private List<RegionAddressDto> regionListAddressDto;

}