package com.profect.delivery.global.exception.custom;

import com.profect.delivery.global.exception.custom.DefaultErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ReviewErrorCode implements DefaultErrorCode {
    NOT_FOUND_REVIEW(HttpStatus.NOT_FOUND, "리뷰를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}