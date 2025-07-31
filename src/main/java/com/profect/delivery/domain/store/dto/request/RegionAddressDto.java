package com.profect.delivery.domain.store.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegionAddressDto {
        @JsonProperty("region_1_address")
        private String address1;

        @JsonProperty("region_2_address")
        private String address2;

        @JsonProperty("region_3_address")
        private String address3;
}