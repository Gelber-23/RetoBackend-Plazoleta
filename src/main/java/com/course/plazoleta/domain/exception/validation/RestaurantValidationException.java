package com.course.plazoleta.domain.exception.validation;

import java.util.List;

public class RestaurantValidationException  extends RuntimeException {
    private final List<String> errors;

    public RestaurantValidationException(List<String> errors) {
        super("Errors: " + errors);
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}