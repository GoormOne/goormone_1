package com.profect.delivery.global.exception.custom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements DefaultErrorCode {

    //404 NOT FOUND
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
    NOT_FOUND_ADDRESS(HttpStatus.NOT_FOUND, "주소를 찾을 수 없습니다.")
    ;

    private HttpStatus httpStatus;
    private String message;

//    // 여기부터 release 1.0.2 ㅇㅣ후로 추가
//    // ✅ DefaultErrorCode 인터페이스 구현용 메서드
//    @Override
//    public HttpStatus getHttpStatus() {
//        return httpStatus;
//    }
//
//    @Override
//    public String getMessage() {
//        return message;
//    }
}