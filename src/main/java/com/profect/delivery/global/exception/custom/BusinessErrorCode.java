package com.profect.delivery.global.exception.custom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BusinessErrorCode implements DefaultErrorCode {


    ALREADY_DELETED(HttpStatus.GONE, "이미 삭제된 정보입니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 정보입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR , "서버 내부 오류입니다."),
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 올바르지 않습니다."),
    CALCULATION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "계산 중 오류가 발생했습니다."),
    EMPTY_SEARCH_RESULT(HttpStatus.NOT_FOUND, "검색 결과가 없습니다."),
    NOT_FOUND_CATEGORY(HttpStatus.NOT_FOUND , "해당 카테고리가 존재하지 않습니다."),
    ;

    private HttpStatus httpStatus;
    private String message;
}