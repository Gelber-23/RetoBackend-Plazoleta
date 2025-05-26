package com.course.plazoleta.domain.exception;

import com.course.plazoleta.domain.utils.constants.ExceptionsConstants;

public class OrderNotPossibleCancel extends RuntimeException {

    public OrderNotPossibleCancel() {
        super(ExceptionsConstants.NOT_POSSIBLE_CANCEL_ORDER_EXCEPTION);
    }

}