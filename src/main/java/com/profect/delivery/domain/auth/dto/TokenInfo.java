package com.profect.delivery.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenInfo {
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Long expiresIn;
    
    public static TokenInfo of(String accessToken, String refreshToken, Long expiresIn) {
        return new TokenInfo("Bearer", accessToken, refreshToken, expiresIn);
    }
}