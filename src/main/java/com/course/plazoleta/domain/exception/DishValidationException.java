package com.course.plazoleta.domain.exception;

import java.util.List;

public class DishValidationException extends RuntimeException {
    private final List<String> errors;

    public DishValidationException(List<String> errors) {
        super("Errors: " + errors);
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}