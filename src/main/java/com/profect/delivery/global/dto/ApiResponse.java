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

    public static <T> ApiResponse<T> fail(DefaultErrorCode errorCode, String path) {
        return new ApiResponse<>(false, null,
                ErrorResponse.of(errorCode.getHttpStatus().value(), errorCode.getMessage(), path));
    }

    public static <T> ApiResponse<T> validFail(int statusCode, String errorMessage, String path) {
        return new ApiResponse<>(false, null, ErrorResponse.of(statusCode, errorMessage, path));
    }
}

//package com.profect.delivery.global.dto;
//
//import com.fasterxml.jackson.annotation.JsonProperty;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class ApiResponse<T> {
//    @JsonProperty("isSuccess")
//    private boolean isSuccess;
//
//    private T data;
//    private ErrorResponse error;
//
//    public static <T> ApiResponse<T> success(T data) {
//        return new ApiResponse<>(true, data, null);
//    }
//
//    public static <T> ApiResponse<T> failure(ErrorResponse error) {
//        return new ApiResponse<>(false, null, error);
//    }
//}
