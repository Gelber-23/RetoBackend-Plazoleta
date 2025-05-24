package com.course.plazoleta.application.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DishRequestTest {
    private Validator validator;

    @BeforeEach
    void setupValidator() {
        ValidatorFactory factory = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(new ParameterMessageInterpolator())
                .buildValidatorFactory();

        validator = factory.getValidator();
    }

    @Test
    void validDishRequest_shouldHaveNoViolations() {
        DishRequest request = new DishRequest();
        request.setName("Grilled Chicken");
        request.setPrice(15000);
        request.setDescription("Tasty grilled chicken with sides");
        request.setUrlImage("https://example.com/image.jpg");
        request.setIdCategory(1L);
        request.setIdRestaurant(1L);

        Set<ConstraintViolation<DishRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    void blankFields_shouldTriggerViolations() {
        DishRequest request = new DishRequest();
        request.setName("");  // blank
        request.setPrice(0);  // invalid
        request.setDescription("   ");  // blank
        request.setUrlImage("invalid-url");  // invalid URL
        request.setIdCategory(0L);  // invalid
        request.setIdRestaurant(-1L);  // invalid

        Set<ConstraintViolation<DishRequest>> violations = validator.validate(request);
        assertEquals(6, violations.size());
    }

    @Test
    void nullValues_shouldTriggerViolations() {
        DishRequest request = new DishRequest();
        request.setName(null);
        request.setPrice(null);
        request.setDescription(null);
        request.setUrlImage(null);


        Set<ConstraintViolation<DishRequest>> violations = validator.validate(request);
        assertEquals(6, violations.size());

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("price")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("description")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("urlImage")));
    }

}