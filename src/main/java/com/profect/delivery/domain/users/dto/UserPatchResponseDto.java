package com.profect.delivery.domain.users.dto;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class UserPatchResponseDto {
    private boolean isSuccess;
    private Object data;
    private String message;
    private Object error;

}
