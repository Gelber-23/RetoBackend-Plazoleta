package com.course.plazoleta.domain.exception;

import com.course.plazoleta.domain.utils.constants.ExceptionsConstants;

public class OrderAlreadyCancelledException extends RuntimeException {

    public OrderAlreadyCancelledException() {
        super(ExceptionsConstants.ORDER_ALREADY_CANCELLED_EXCEPTION);
    }

}
