package com.profect.delivery.global.exception.custom;

import org.springframework.http.HttpStatus;

public interface DefaultErrorCode {
    HttpStatus getHttpStatus();
    String getMessage();
}
