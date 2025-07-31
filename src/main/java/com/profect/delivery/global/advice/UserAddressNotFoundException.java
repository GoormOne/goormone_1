package com.profect.delivery.global.advice;

public class UserAddressNotFoundException extends RuntimeException {
    public UserAddressNotFoundException(String message) {
        super(message);
    }
}
