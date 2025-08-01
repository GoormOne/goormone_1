package com.profect.delivery.global.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private int code;             // 커스텀 에러 코드
    private String message;       // 에러 메시지
    private String path;          // 요청 URI
    private String timestamp;     // 에러 발생 시점
}