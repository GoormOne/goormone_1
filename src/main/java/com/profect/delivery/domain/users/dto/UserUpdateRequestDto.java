package com.profect.delivery.domain.users.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.nio.file.FileStore;

@Data
@NoArgsConstructor
public class UserUpdateRequestDto {//네이밍 규칙 바꾸기 알아보게
    @Size(min = 2, max = 50, message = "이름은 2자 이상 50자 이하여야 합니다.")
    private String name;
    private String password;

    @Email(message = "유효한 이메일 형식이 아닙니다.")
    private String email;
    private Boolean is_public;


}
