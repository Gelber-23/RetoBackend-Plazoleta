package com.course.plazoleta.application.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DishRequestTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidDishRequest() {
        DishRequest request = new DishRequest();
        request.setName("Burger");
        request.setPrice(100);
        request.setDescription("Juicy beef burger");
        request.setUrlImage("https://example.com/image.jpg");
        request.setIdCategory(1L);
        request.setIdRestaurant(1L);
        Set<ConstraintViolation<DishRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty(), "There should be no violations for a valid request");
    }

    @Test
    void testBlankName() {
        DishRequest request = buildValidDishRequest();
        request.setName("");

        Set<ConstraintViolation<DishRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")));
    }

    @Test
    void testNullPrice() {
        DishRequest request = buildValidDishRequest();
        request.setPrice(null);

        Set<ConstraintViolation<DishRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("price")));
    }

    @Test
    void testPriceZero() {
        DishRequest request = buildValidDishRequest();
        request.setPrice(0);

        Set<ConstraintViolation<DishRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testBlankDescription() {
        DishRequest request = buildValidDishRequest();
        request.setDescription("");

        Set<ConstraintViolation<DishRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("description")));
    }

    @Test
    void testInvalidUrl() {
        DishRequest request = buildValidDishRequest();
        request.setUrlImage("not-a-url");

        Set<ConstraintViolation<DishRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("urlImage")));
    }



    @Test
    void testNegativeCategory() {
        DishRequest request = buildValidDishRequest();
        request.setIdCategory(0);

        Set<ConstraintViolation<DishRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }

    private DishRequest buildValidDishRequest() {
        DishRequest request = new DishRequest();
        request.setName("Pizza");
        request.setPrice(50);
        request.setDescription("Delicious cheese pizza");
        request.setUrlImage("https://example.com/pizza.jpg");
        request.setIdCategory(2);
        request.setIdRestaurant(1);
        return request;
    }
}