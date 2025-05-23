package com.course.plazoleta.domain.exception;


import com.course.plazoleta.domain.utils.constants.ExceptionsConstants;

public class UserNotOwnerException extends RuntimeException {

    public UserNotOwnerException() {
        super(ExceptionsConstants.USER_NOT_OWNER_EXCEPTION);
    }

}

