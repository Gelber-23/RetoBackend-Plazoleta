package com.course.plazoleta.application.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantRequestTest {

    private Validator validator;

    @BeforeEach
    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldPassValidationWithValidData() {
        RestaurantRequest request = new RestaurantRequest();
        request.setName("Good Food");
        request.setAddress("123 Main Street");
        request.setId_owner(1L);
        request.setPhone("+12345678901");
        request.setUrlLogo("https://example.com/logo.png");
        request.setNit("123456789");

        Set<ConstraintViolation<RestaurantRequest>> violations = validator.validate(request);

        assertTrue(violations.isEmpty(), "Expected no validation errors");
    }

    @Test
    void shouldFailWhenNameIsBlank() {
        RestaurantRequest request = createValidRequest();
        request.setName("");

        Set<ConstraintViolation<RestaurantRequest>> violations = validator.validate(request);

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")));
    }

    @Test
    void shouldFailWhenNameIsOnlyNumbers() {
        RestaurantRequest request = createValidRequest();
        request.setName("123456");

        Set<ConstraintViolation<RestaurantRequest>> violations = validator.validate(request);

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")));
    }

    @Test
    void shouldFailWhenAddressIsBlank() {
        RestaurantRequest request = createValidRequest();
        request.setAddress("");

        Set<ConstraintViolation<RestaurantRequest>> violations = validator.validate(request);

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("address")));
    }

    @Test
    void shouldFailWhenIdOwnerIsZero() {
        RestaurantRequest request = createValidRequest();
        request.setId_owner(0L);

        Set<ConstraintViolation<RestaurantRequest>> violations = validator.validate(request);

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("id_owner")));
    }

    @Test
    void shouldFailWhenPhoneIsInvalid() {
        RestaurantRequest request = createValidRequest();
        request.setPhone("++1234abc");

        Set<ConstraintViolation<RestaurantRequest>> violations = validator.validate(request);

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("phone")));
    }

    @Test
    void shouldFailWhenUrlLogoIsInvalid() {
        RestaurantRequest request = createValidRequest();
        request.setUrlLogo("not_a_url");

        Set<ConstraintViolation<RestaurantRequest>> violations = validator.validate(request);

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("urlLogo")));
    }

    @Test
    void shouldFailWhenNitIsNotNumeric() {
        RestaurantRequest request = createValidRequest();
        request.setNit("ABC123");

        Set<ConstraintViolation<RestaurantRequest>> violations = validator.validate(request);

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nit")));
    }

    private RestaurantRequest createValidRequest() {
        RestaurantRequest request = new RestaurantRequest();
        request.setName("Good Food");
        request.setAddress("123 Main Street");
        request.setId_owner(1L);
        request.setPhone("+12345678901");
        request.setUrlLogo("https://example.com/logo.png");
        request.setNit("123456789");
        return request;
    }
}