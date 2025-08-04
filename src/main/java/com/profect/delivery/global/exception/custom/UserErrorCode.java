package com.profect.delivery.global.exception.custom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements DefaultErrorCode {

    //404 NOT FOUND
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
    ;

    private HttpStatus httpStatus;
    private String message;
}
