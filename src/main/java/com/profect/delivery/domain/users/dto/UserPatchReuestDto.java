package com.profect.delivery.domain.users.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Data
@NoArgsConstructor
public class UserPatchReuestDto {

    private String username;
    private String password;
    private String name;
    private Date brith;
    private String email;


}
