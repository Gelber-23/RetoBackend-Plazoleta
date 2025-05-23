package com.course.plazoleta.domain.exception;


public class UserIsNotOwnerValidationException  extends RuntimeException {

    public UserIsNotOwnerValidationException(String message) {
        super(message);
    }

}

