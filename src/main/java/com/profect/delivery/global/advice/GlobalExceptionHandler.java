package com.profect.delivery.global.advice;


import com.profect.delivery.global.dto.ApiResponse;
import com.profect.delivery.global.exception.BusinessException;
import com.profect.delivery.global.exception.custom.BusinessErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {BusinessException.class})
    public ResponseEntity<ApiResponse<BusinessErrorCode>> handleBusinessException(BusinessException e) {
        return ResponseEntity
                .status(e.getDefaultErrorCode().getHttpStatus())
                .body(ApiResponse.fail(e.getDefaultErrorCode()));
    }


}


