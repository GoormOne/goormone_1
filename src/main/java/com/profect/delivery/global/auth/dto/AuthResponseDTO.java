package com.profect.delivery.global.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class AuthResponseDTO {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class AuthRegisterResponseDTO {
        private String userId;
        private String username;
        private String message;
    }
}