package com.profect.delivery.global.advice;

import com.profect.delivery.global.dto.ApiResponse;
import com.profect.delivery.global.dto.ErrorResponse;
import com.profect.delivery.global.exception.ConflictException;
import com.profect.delivery.global.exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionalHandler {

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiResponse<?>> handleConflictException(ConflictException e,
                                                                  HttpServletRequest request) {
        ErrorResponse err = ErrorResponse.of(40901, e.getMessage(), request.getRequestURI());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ApiResponse.failure(err));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleNotFoundException(NotFoundException e,
                                                                  HttpServletRequest request) {
        ErrorResponse err = ErrorResponse.of(40401, e.getMessage(), request.getRequestURI());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.failure(err));
    }
}
