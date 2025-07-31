package com.profect.delivery.domain.store.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class StoreRegisterDto {

    @NotBlank(message = "가게 이름은 필수입니다.")
    private String storeName;


    private String userId;
    private String category;

    private String storeDescription;

    @NotBlank(message = "가게 주소는 필수입니다.")
    private String address1;

    private String address2;


    private String zipCd;

    @NotBlank(message = "가게 전화번호는 필수입니다.")
    private String storePhone;


    private BigDecimal storeLatitude;
    private BigDecimal storeLongitude;

}