package com.course.plazoleta.application.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DishUpdateRequestTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    private DishUpdateRequest buildValidDishUpdateRequest() {
        DishUpdateRequest request = new DishUpdateRequest();

        request.setPrice(50);
        request.setDescription("Delicious cheese pizza");

        return request;
    }
    @Test
    void testValidDishUpdateRequest() {
        DishUpdateRequest request = new DishUpdateRequest();
       
        request.setPrice(100);
        request.setDescription("Juicy beef burger");
     
        Set<ConstraintViolation<DishUpdateRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty(), "There should be no violations for a valid request");
    }

    @Test
    void testNullPrice() {
        DishUpdateRequest request = buildValidDishUpdateRequest();
        request.setPrice(null);

        Set<ConstraintViolation<DishUpdateRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("price")));
    }

    @Test
    void testPriceZero() {
        DishUpdateRequest request = buildValidDishUpdateRequest();
        request.setPrice(0);

        Set<ConstraintViolation<DishUpdateRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testBlankDescription() {
        DishUpdateRequest request = buildValidDishUpdateRequest();
        request.setDescription("");

        Set<ConstraintViolation<DishUpdateRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("description")));
    }


}