package com.profect.delivery.global.exception.custom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BusinessErrorCode implements DefaultErrorCode {


    ALREADY_DELETED(HttpStatus.GONE, "이미 삭제된 정보입니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 정보입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR , "서버 내부 오류입니다."),
    ;

    private HttpStatus httpStatus;
    private String message;
}
