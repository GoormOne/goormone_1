package com.profect.delivery.domain.store.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;



public record RegionDto(
        List<String> regionIds
)  {

}
