package com.profect.delivery.global.dto;

import com.profect.delivery.global.exception.custom.DefaultErrorCode;
import org.springframework.http.HttpStatus;

public record ApiResponse<T> (
        boolean isSuccess,
        T data,
        ErrorResponse error
) {

    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(true, null, null);
    }


    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data, null);
    }

    public static <T> ApiResponse<T> fail(DefaultErrorCode errorCode) {
        return new ApiResponse<>(false, null, ErrorResponse.of(errorCode.getHttpStatus().value(), errorCode.getMessage()));
    }

    public static <T> ApiResponse<T> validFail(String errorMessage) {
        return new ApiResponse<>(false, null, ErrorResponse.of(HttpStatus.BAD_REQUEST.value(), errorMessage));
    }
}
