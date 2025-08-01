package com.profect.delivery.common.error.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum StoreErrorCode implements DefaultErrorCode {
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "가게를 찾을 수 없습니다. "),
    STORE_FORBIDDEN(HttpStatus.FORBIDDEN, "해당 가게에 대한 권한이 없습니다. ");

    private final HttpStatus httpStatus;
    private final String message;

}