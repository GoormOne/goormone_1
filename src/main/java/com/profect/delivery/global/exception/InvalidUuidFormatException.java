package com.profect.delivery.global.exception;

public class InvalidUuidFormatException extends RuntimeException {
    public InvalidUuidFormatException(String message) {
        super(message);
    }
}