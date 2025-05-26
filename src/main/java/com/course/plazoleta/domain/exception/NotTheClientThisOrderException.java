package com.course.plazoleta.domain.exception;

import com.course.plazoleta.domain.utils.constants.ExceptionsConstants;

public class NotTheClientThisOrderException extends RuntimeException {

    public NotTheClientThisOrderException() {
        super(ExceptionsConstants.NOT_CLIENT_OF_THIS_ORDER_EXCEPTION);
    }

}