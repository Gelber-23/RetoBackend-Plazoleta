package com.course.plazoleta.application.dto.request;

import com.course.plazoleta.domain.utils.constants.DtoConstants;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DishUpdateRequestTest {
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
    void validDishUpdateRequest_shouldHaveNoViolations() {
        DishUpdateRequest request = new DishUpdateRequest();
        request.setId(1L);
        request.setDescription("Delicious chicken");
        request.setPrice(10000);

        Set<ConstraintViolation<DishUpdateRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    void invalidDishUpdateRequest_shouldReturnViolations() {
        DishUpdateRequest request = new DishUpdateRequest();
        request.setId(0L);
        request.setDescription("");
        request.setPrice(0);

        Set<ConstraintViolation<DishUpdateRequest>> violations = validator.validate(request);
        assertEquals(3, violations.size());
    }

    @Test
    void nullValues_shouldReturnViolations() {
        DishUpdateRequest request = new DishUpdateRequest();
        request.setDescription(null);
        request.setPrice(null);
        request.setId(1L);
        Set<ConstraintViolation<DishUpdateRequest>> violations = validator.validate(request);

        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("description")
                        && v.getMessage().equals(DtoConstants.FIELD_REQUIRED)));

        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("price")
                        && v.getMessage().equals(DtoConstants.FIELD_REQUIRED)));
    }

}