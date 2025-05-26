package com.course.plazoleta.domain.exception;

public class SmsNotSendException extends RuntimeException {

    public SmsNotSendException(String message) {
        super(message);
    }

}