package com.course.plazoleta.domain.exception;

import com.course.plazoleta.domain.utils.constants.ExceptionsConstants;

public class PinNotMatchException extends RuntimeException {

    public PinNotMatchException() {
        super(ExceptionsConstants.PIN_NOT_MATCH_EXCEPTION);
    }

}