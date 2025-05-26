package com.course.plazoleta.domain.exception.validation;

import java.util.List;

public class OrderValidationException   extends RuntimeException {
    private final List<String> errors;

    public OrderValidationException(List<String> errors) {
        super("Errors: " + errors);
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}