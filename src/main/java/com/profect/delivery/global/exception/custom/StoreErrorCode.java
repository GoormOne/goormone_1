package com.profect.delivery.global.exception.custom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum StoreErrorCode  implements DefaultErrorCode{

    NOT_FOUND_STORE(HttpStatus.NOT_FOUND, "매장을 찾을 수 없습니다."),
    ALREADY_DELETED_STORE(HttpStatus.GONE, "이미 삭제된 매장입니다."),
    NOT_FOUND_REGION(HttpStatus.NOT_FOUND, "해당 지역을 찾을 수 없습니다."),
    NOT_FOUND_STORE_REGION(HttpStatus.NOT_FOUND, "매장-지역 연결이 존재하지 않습니다."),

    ;


    private HttpStatus httpStatus;
    private String message;
}