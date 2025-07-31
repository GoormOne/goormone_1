package com.profect.delivery.domain.users.dto;

import com.profect.delivery.global.entity.UserAddress;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

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

    public UserAddress toEntity(String userid){
        return UserAddress.builder()
                .addressCd(UUID.randomUUID())
                .userId(userid)
                .addressName(address_name)
                .address1(address1)
                .address2(address2)
                .zipCd(zip_cd)
                .userLatitude(user_latitude)
                .userLongitude(user_longitude)
                .isDefault(is_default)
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .build();
    }

}
