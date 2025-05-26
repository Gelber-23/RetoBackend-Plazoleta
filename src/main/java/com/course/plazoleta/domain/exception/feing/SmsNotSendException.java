package com.course.plazoleta.domain.exception.feing;

public class SmsNotSendException extends RuntimeException {

    public SmsNotSendException(String message) {
        super(message);
    }

}