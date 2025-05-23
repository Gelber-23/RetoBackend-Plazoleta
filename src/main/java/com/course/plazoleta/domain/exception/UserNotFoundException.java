package com.course.plazoleta.domain.exception;

import com.course.plazoleta.domain.utils.constants.ExceptionsConstants;

public class UserNotFoundException  extends RuntimeException {

    public UserNotFoundException(Long userId) {
        super(ExceptionsConstants.USER_NOT_FOUND_EXCEPTION + ": " + userId);
    }

}
