package com.profect.delivery.global.exception;


import com.profect.delivery.global.exception.custom.DefaultErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BusinessException extends RuntimeException {
    private final DefaultErrorCode defaultErrorCode;
}
