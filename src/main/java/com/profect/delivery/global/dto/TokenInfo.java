package com.profect.delivery.global.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
// 클라이언트에 토큰을 보내기 위한 DTO
public class TokenInfo {
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Long expiresIn;
    
    public static TokenInfo of(String accessToken, String refreshToken, Long expiresIn) {
        return new TokenInfo("Bearer", accessToken, refreshToken, expiresIn);
    }
}