package com.profect.delivery.domain.users.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserUpdateRequestDto {//네이밍 규칙 바꾸기 알아보게

    private String name;
    private String password;
    private String email;
    private Boolean is_public;

}
