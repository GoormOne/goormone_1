package com.profect.delivery.global.exception.custom;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GoogleAIErrorCode implements DefaultErrorCode{

    GOOGLE_AI_ERROR_CODE(HttpStatus.INTERNAL_SERVER_ERROR, "Google AI 응답 생성에 실패하였습니다."),
    ;



    private HttpStatus httpStatus;
    private String message;
}