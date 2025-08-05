package com.profect.delivery.global.exception.custom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum OrderErrorCode implements DefaultErrorCode{



    NOT_FOUND_ORDER(HttpStatus.NOT_FOUND, "해당 주문을 찾을 수 없습니다."),
    NOT_FOUND_CART(HttpStatus.NOT_FOUND, "해당 내역을 찾을 수 없습니다."),
    NOT_FOUND_ITEM(HttpStatus.NOT_FOUND, "해당 아이템을 찾을 수 없습니다."),

    ;


    private HttpStatus httpStatus;
    private String message;
}
