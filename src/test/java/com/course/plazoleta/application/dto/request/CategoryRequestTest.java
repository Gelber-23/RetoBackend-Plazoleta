package com.course.plazoleta.application.dto.request;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CategoryRequestTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validCategoryRequestHasNoViolations() {
        CategoryRequest request = new CategoryRequest();
        request.setName("Fast Food");
        request.setDescription("Quick meals on the go");

        Set<ConstraintViolation<CategoryRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty(), "Expected no violations for a valid request");
    }

    @Test
    void nameBlankFailsValidation() {
        CategoryRequest request = buildValidRequest();
        request.setName("");

        Set<ConstraintViolation<CategoryRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("name")
                        && v.getMessage().equals("Name is required"))
        );
    }

    @Test
    void nameNullFailsValidation() {
        CategoryRequest request = buildValidRequest();
        request.setName(null);

        Set<ConstraintViolation<CategoryRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("name")
                        && v.getMessage().equals("Name is required"))
        );
    }

    @Test
    void descriptionBlankFailsValidation() {
        CategoryRequest request = buildValidRequest();
        request.setDescription("");

        Set<ConstraintViolation<CategoryRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("description")
                        && v.getMessage().equals("Description is required"))
        );
    }

    @Test
    void descriptionNullFailsValidation() {
        CategoryRequest request = buildValidRequest();
        request.setDescription(null);

        Set<ConstraintViolation<CategoryRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("description")
                        && v.getMessage().equals("Description is required"))
        );
    }

    private CategoryRequest buildValidRequest() {
        CategoryRequest req = new CategoryRequest();
        req.setName("Desserts");
        req.setDescription("Sweet dishes");
        return req;
    }
}