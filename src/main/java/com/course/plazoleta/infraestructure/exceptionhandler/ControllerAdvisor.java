package com.course.plazoleta.infraestructure.exceptionhandler;

import com.course.plazoleta.domain.exception.*;
import com.course.plazoleta.domain.exception.NoDataFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {

    private static final String MESSAGE = "Message";




    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoDataFoundException(
            NoDataFoundException ignoredNoDataFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.NO_DATA_FOUND.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFound(
            UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(
                        MESSAGE,
                        ex.getMessage()
                ));
    }
    @ExceptionHandler(ClientHaveOrderActiveException.class)
    public ResponseEntity<Map<String, String>> handleClientHaveOrderActive (
            ClientHaveOrderActiveException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(
                        MESSAGE,
                        ex.getMessage()
                ));
    }
    @ExceptionHandler(UserNotOwnerException.class)
    public ResponseEntity<Map<String, String>> handleUserNotOwner(
            UserNotOwnerException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(
                        MESSAGE,
                        ex.getMessage()
                ));
    }
    @ExceptionHandler(UserIsNotOwnerValidationException.class)
    public ResponseEntity<Map<String, String>> handleUserNotOwner(
            UserIsNotOwnerValidationException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(
                        MESSAGE,
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(RestaurantValidationException.class)
    public ResponseEntity<Map<String, List<String>>> handleRestaurantValidation(
            RestaurantValidationException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap("errors", ex.getErrors()));
    }

    @ExceptionHandler(DishValidationException.class)
    public ResponseEntity<Map<String, List<String>>> handleRestaurantValidation(
            DishValidationException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap("errors", ex.getErrors()));
    }

    @ExceptionHandler(OrderValidationException.class)
    public ResponseEntity<Map<String, List<String>>> handleOrderValidation(
            OrderValidationException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap("errors", ex.getErrors()));
    }
    @ExceptionHandler(ExternalServiceException.class)
    public ResponseEntity<Map<String, String>> handleExternalServiceError(
            ExternalServiceException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Collections.singletonMap(
                        MESSAGE,
                       ex.getCause().toString()
                ));
    }
    @ExceptionHandler(DishNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleDishNotFound (
            DishNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(
                        MESSAGE,
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(UserIsNotEmployeeRestaurantException.class)
    public ResponseEntity<Map<String, String>> handleUserNotHaveIdRestaurant(
            UserIsNotEmployeeRestaurantException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(
                        MESSAGE,
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(NotEmployeeUserException.class)
    public ResponseEntity<Map<String, String>> handleUserNotEmployee(
            NotEmployeeUserException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(
                        MESSAGE,
                        ex.getMessage()
                ));
    }
    @ExceptionHandler(SmsNotSendException.class)
    public ResponseEntity<Map<String, String>> handleSmsNotSend(
            SmsNotSendException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(
                        MESSAGE,
                        ex.getMessage()
                ));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }


}
