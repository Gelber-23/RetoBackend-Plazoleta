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

class RestaurantRequestTest {
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
    void validRestaurantRequest_shouldHaveNoViolations() {
        RestaurantRequest request = new RestaurantRequest();
        request.setName("Pepe Grill");
        request.setAddress("123 Main Street");
        request.setId_owner(1L);
        request.setPhone("+123456789012");
        request.setUrlLogo("https://example.com/logo.png");
        request.setNit("123456");

        Set<ConstraintViolation<RestaurantRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    void invalidRestaurantRequest_shouldReturnViolations() {
        RestaurantRequest request = new RestaurantRequest();
        request.setName("123456"); // solo números
        request.setAddress(""); // en blanco
        request.setId_owner(0); // menor que mínimo
        request.setPhone("abc"); // formato inválido
        request.setUrlLogo("invalid-url"); // formato de URL inválido
        request.setNit("abc123"); // no solo números

        Set<ConstraintViolation<RestaurantRequest>> violations = validator.validate(request);
        assertEquals(6, violations.size());
    }

    @Test
    void phoneTooLong_shouldFailValidation() {
        RestaurantRequest request = new RestaurantRequest();
        request.setName("Pepe Grill");
        request.setAddress("123 Main Street");
        request.setId_owner(1L);
        request.setPhone("+123456789012345678"); // demasiado largo
        request.setUrlLogo("https://example.com/logo.png");
        request.setNit("123456");

        Set<ConstraintViolation<RestaurantRequest>> violations = validator.validate(request);

        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals(DtoConstants.FIELD_MUST_HAVE_13_CHARACTERS)));
    }

}