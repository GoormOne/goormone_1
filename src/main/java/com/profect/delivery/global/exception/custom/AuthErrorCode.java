package com.profect.delivery.global.exception.custom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements DefaultErrorCode {

    //400 error (BAD REQUEST)
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "유효하지 않은 요청입니다."),

    //401  error (UNAUTHORIZED)
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증되지 않은 요청입니다."),
    UNKNOWNTOKEN(HttpStatus.UNAUTHORIZED, "알 수 없는 토큰입니다."),


    //403 error (FORBIDDEN)
    FORBIDDEN(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    ROLE_OWNER_ONLY(HttpStatus.FORBIDDEN, "이 작업은 OWNER만 수행할 수 있습니다."),
    ROLE_MANAGER_ONLY(HttpStatus.FORBIDDEN, "이 작업은 MANAGER만 수행할 수 있습니다."),
    ROLE_MASTER_ONLY(HttpStatus.FORBIDDEN, "이 작업은 MASTER만 수행할 수 있습니다."),
    ROLE_CUSTOMER_ONLY(HttpStatus.FORBIDDEN, "이 작업은 CUSTOMER만 수행할 수 있습니다."),

    //404 error (NOT FOUND)
    NOT_FOUND_REFRESH_TOKEN(HttpStatus.NOT_FOUND , "존재하지 않는 리프레시 토큰입니다."),
    ;
    private final HttpStatus httpStatus;
    private final String message;
}
