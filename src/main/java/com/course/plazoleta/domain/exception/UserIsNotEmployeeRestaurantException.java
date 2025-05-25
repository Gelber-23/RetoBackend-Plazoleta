package com.course.plazoleta.domain.exception;

import com.course.plazoleta.domain.utils.constants.ExceptionsConstants;

public class UserIsNotEmployeeRestaurantException extends RuntimeException {

    public UserIsNotEmployeeRestaurantException() {
        super(ExceptionsConstants.NOT_EMPLOYEE_RESTAURANT_USER_EXCEPTION );
    }

}