package com.profect.delivery.global.exception.custom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MenuErrorCode  implements DefaultErrorCode{

    NOT_FOUND_MENU(HttpStatus.NOT_FOUND, "해당 메뉴를 찾을 수 없습니다."),

    ;


    private HttpStatus httpStatus;
    private String message;
}
