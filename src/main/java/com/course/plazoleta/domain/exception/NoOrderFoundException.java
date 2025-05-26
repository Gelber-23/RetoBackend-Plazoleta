package com.course.plazoleta.domain.exception;

import com.course.plazoleta.domain.utils.constants.ExceptionsConstants;

public class NoOrderFoundException extends RuntimeException {

    public NoOrderFoundException() {
        super(ExceptionsConstants.ORDER_NOT_FOUND_EXCEPTION);
    }

}
