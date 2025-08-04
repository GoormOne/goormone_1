package com.profect.delivery.domain.users.dto;

import com.profect.delivery.global.entity.User;
import com.profect.delivery.global.entity.UserAddress;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class UserAddressesResponseDto {

    private String address_name;
    private String address1;
    private String address2;
    private String zip_cd;




    public static UserAddressesResponseDto fromEntity(UserAddress address) {
        UserAddressesResponseDto dto = new UserAddressesResponseDto();
        dto.address_name = address.getAddressName();
        dto.address1 = address.getAddress1();
        dto.address2 = address.getAddress2();
        dto.zip_cd = address.getZipCd();
        return dto;
    }


}