package com.profect.delivery.common.error.code;

import org.springframework.http.HttpStatus;

public interface DefaultErrorCode {
    HttpStatus getHttpStatus();
    String getMessage();

}
