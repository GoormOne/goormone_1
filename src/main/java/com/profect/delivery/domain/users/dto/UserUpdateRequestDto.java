package com.profect.delivery.domain.users.dto;

import com.profect.delivery.global.entity.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class UserPatchRequestDto {//네이밍 규칙 바꾸기 알아보게

    private String name;
    private String password;
    private String email;

    private Boolean is_public;

}
