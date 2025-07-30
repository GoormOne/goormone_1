package com.profect.delivery.domain.users.dto;

import com.profect.delivery.global.entity.UserAddress;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class UserAddressesRequestDto {


    private String address_name;
    private String address1;
    private String address2;
    private String zip_cd;
    private BigDecimal user_latitude;
    private BigDecimal user_longitude;
    private boolean is_default;

//    public UserAddress toEntity(){
//        return UserAddress.builder()
//                .addressName(address_name)
//                .
//    }

}
