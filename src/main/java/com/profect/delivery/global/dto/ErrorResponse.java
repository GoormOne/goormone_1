package com.profect.delivery.global.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {
    private int code;           // 커스텀 에러 코드
    private String message;     // 사용자용 에러 메세지
    private String path;        // 요청 URI
    //    private String timestamp;   // 에러 발생 지점
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    // ErrorResponse.java
    public static ErrorResponse of(int code, String message, String path) {
        return ErrorResponse.builder()
                .code(code)
                .message(message)
                .path(path)
                .timestamp(LocalDateTime.now())
                .build();
    }
}