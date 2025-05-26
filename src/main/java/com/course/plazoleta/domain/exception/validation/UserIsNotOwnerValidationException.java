package com.course.plazoleta.domain.exception.validation;


public class UserIsNotOwnerValidationException  extends RuntimeException {

    public UserIsNotOwnerValidationException(String message) {
        super(message);
    }

}

