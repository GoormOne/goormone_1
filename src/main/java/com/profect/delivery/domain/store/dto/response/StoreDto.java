package com.profect.delivery.domain.store.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreDto {

    private String storeId;
    private String storeName;
    private String description;
    private String category;

    private AddressDto address;           // 주소 객체
    private String zip_cd;
    private String store_phone;

    private StoreLocationDto store_location;  // 위치 객체

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddressDto {
        private String address1;  // 상세 주소 제외
        private String address2;  // 상세 주소
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StoreLocationDto {
        private String store_latitude;
        private String store_longtitude;
    }

//    public static record RegionDto(
//            List<String> regionIds
//    )  {
//
//    }
}