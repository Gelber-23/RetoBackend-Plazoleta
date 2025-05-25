package com.course.plazoleta.domain.exception;

import com.course.plazoleta.domain.utils.constants.ExceptionsConstants;

public class ClientHaveOrderActiveException extends RuntimeException {

    public ClientHaveOrderActiveException() {
        super(ExceptionsConstants.CLIENT_HAVE_ORDER_ACTIVE_EXCEPTION);
    }

}
