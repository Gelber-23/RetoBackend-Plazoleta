package com.course.plazoleta.domain.exception;

import com.course.plazoleta.domain.utils.constants.ExceptionsConstants;

public class NotEmployeeUserException extends RuntimeException {

    public NotEmployeeUserException() {
        super(ExceptionsConstants.NOT_EMPLOYEE_USER_EXCEPTION);
    }

}
