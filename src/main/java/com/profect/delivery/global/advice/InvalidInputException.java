package com.profect.delivery.global.advice;

class InvalidInputException extends RuntimeException {
    public InvalidInputException(String message) {
        super(message);
    }
}