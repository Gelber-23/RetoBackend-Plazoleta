package com.course.plazoleta.domain.exception;

import com.course.plazoleta.domain.utils.constants.ExceptionsConstants;

public class DishNotFoundException extends RuntimeException {

    public DishNotFoundException() {
        super(ExceptionsConstants.DISH_NOT_FOUND_EXCEPTION);
    }

}
