package com.course.plazoleta.infraestructure.exceptionhandler;

import com.course.plazoleta.domain.exception.*;
import com.course.plazoleta.domain.exception.feing.SmsNotSendException;
import com.course.plazoleta.domain.exception.feing.TrackNotCreateException;
import com.course.plazoleta.domain.exception.usersexception.UserIsNotEmployeeRestaurantException;
import com.course.plazoleta.domain.exception.usersexception.UserNotFoundException;
import com.course.plazoleta.domain.exception.usersexception.UserNotOwnerException;
import com.course.plazoleta.domain.exception.validation.DishValidationException;
import com.course.plazoleta.domain.exception.validation.OrderValidationException;
import com.course.plazoleta.domain.exception.validation.RestaurantValidationException;
import com.course.plazoleta.domain.exception.validation.UserIsNotOwnerValidationException;
import com.course.plazoleta.domain.utils.constants.ExceptionsConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class ControllerAdvisorTest {

    private ControllerAdvisor advisor;

    @BeforeEach
    void setUp() {
        advisor = new ControllerAdvisor();
    }

    @Test
    void handleNoDataFoundException() {
        ResponseEntity<Map<String, String>> resp =
                advisor.handleNoDataFoundException(new NoDataFoundException());
        assertEquals(HttpStatus.NOT_FOUND, resp.getStatusCode());
        assertEquals("No data found for the requested petition", Objects.requireNonNull(resp.getBody()).get("Message"));
    }

    @Test
    void handleUserNotFound() {
        UserNotFoundException ex = new UserNotFoundException(99L);
        ResponseEntity<Map<String, String>> resp = advisor.handleUserNotFound(ex);
        assertEquals(HttpStatus.NOT_FOUND, resp.getStatusCode());
        assertEquals(ex.getMessage(), Objects.requireNonNull(resp.getBody()).get("Message"));
    }

    @Test
    void handleClientHaveOrderActive() {
        ClientHaveOrderActiveException ex = new ClientHaveOrderActiveException();
        ResponseEntity<Map<String, String>> resp = advisor.handleClientHaveOrderActive(ex);
        assertEquals(HttpStatus.NOT_FOUND, resp.getStatusCode());
        assertEquals(ex.getMessage(), Objects.requireNonNull(resp.getBody()).get("Message"));
    }

    @Test
    void handleUserNotOwner_andValidation() {
        UserNotOwnerException ex1 = new UserNotOwnerException();
        ResponseEntity<Map<String, String>> r1 = advisor.handleUserNotOwner(ex1);
        assertEquals(HttpStatus.NOT_FOUND, r1.getStatusCode());
        assertEquals(ex1.getMessage(), Objects.requireNonNull(r1.getBody()).get("Message"));

        UserIsNotOwnerValidationException ex2 = new UserIsNotOwnerValidationException(ExceptionsConstants.USER_NOT_OWNER_RESTAURANT_EXCEPTION);
        ResponseEntity<Map<String, String>> r2 = advisor.handleUserNotOwner(ex2);
        assertEquals(HttpStatus.NOT_FOUND, r2.getStatusCode());
        assertEquals(ExceptionsConstants.USER_NOT_OWNER_RESTAURANT_EXCEPTION, ex2.getMessage());
    }

    @Test
    void handleValidationExceptions() {
        List<String> errs = List.of("e1", "e2");
        // RestaurantValidationException
        ResponseEntity<Map<String, List<String>>> rr =
                advisor.handleRestaurantValidation(new RestaurantValidationException(errs));
        assertEquals(HttpStatus.BAD_REQUEST, rr.getStatusCode());
        assertEquals(errs, Objects.requireNonNull(rr.getBody()).get("errors"));
        // DishValidationException
        ResponseEntity<Map<String, List<String>>> rd =
                advisor.handleRestaurantValidation(new DishValidationException(errs));
        assertEquals(HttpStatus.BAD_REQUEST, rd.getStatusCode());
        assertEquals(errs, Objects.requireNonNull(rd.getBody()).get("errors"));
        // OrderValidationException
        ResponseEntity<Map<String, List<String>>> ro =
                advisor.handleOrderValidation(new OrderValidationException(errs));
        assertEquals(HttpStatus.BAD_REQUEST, ro.getStatusCode());
        assertEquals(errs, Objects.requireNonNull(ro.getBody()).get("errors"));
    }

    @Test
    void handleExternalServiceError() {
        RuntimeException cause = new RuntimeException("down");
        ExternalServiceException ex = new ExternalServiceException(cause.getMessage(),cause);


        ResponseEntity<Map<String, String>> resp = advisor.handleExternalServiceError(ex);
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, resp.getStatusCode());
        assertTrue(Objects.requireNonNull(resp.getBody()).get("Message").contains("down"));
    }

    @Test
    void handleDishNotFound_andOthers() {
        // DishNotFoundException
        DishNotFoundException dn = new DishNotFoundException();
        var rDn = advisor.handleDishNotFound(dn);
        assertEquals(HttpStatus.NOT_FOUND, rDn.getStatusCode());
        assertEquals(dn.getMessage(), Objects.requireNonNull(rDn.getBody()).get("Message"));

        // UserIsNotEmployeeRestaurantException
        var ue = new UserIsNotEmployeeRestaurantException();
        var rUe = advisor.handleUserNotHaveIdRestaurant(ue);
        assertEquals(HttpStatus.NOT_FOUND, rUe.getStatusCode());
        assertEquals(ue.getMessage(), Objects.requireNonNull(rUe.getBody()).get("Message"));

        // NotEmployeeUserException
        var ne = new NotEmployeeUserException();
        var rNe = advisor.handleUserNotEmployee(ne);
        assertEquals(HttpStatus.NOT_FOUND, rNe.getStatusCode());
        assertEquals(ne.getMessage(), Objects.requireNonNull(rNe.getBody()).get("Message"));

        // SmsNotSendException
        var ss = new SmsNotSendException("fail");
        var rSs = advisor.handleSmsNotSend(ss);
        assertEquals(HttpStatus.NOT_FOUND, rSs.getStatusCode());
        assertEquals(ss.getMessage(), Objects.requireNonNull(rSs.getBody()).get("Message"));

        // PinNotMatchException
        var pm = new PinNotMatchException();
        var rPm = advisor.handlePinNotMatch(pm);
        assertEquals(HttpStatus.NOT_FOUND, rPm.getStatusCode());
        assertEquals(pm.getMessage(), Objects.requireNonNull(rPm.getBody()).get("Message"));

        // OrderNotPossibleCancel
        var opc = new OrderNotPossibleCancel();
        var rOpc = advisor.handleNotPossibleCancelOrder(opc);
        assertEquals(HttpStatus.NOT_FOUND, rOpc.getStatusCode());
        assertEquals(opc.getMessage(), Objects.requireNonNull(rOpc.getBody()).get("Message"));

        // OrderAlreadyCancelledException
        var oac = new OrderAlreadyCancelledException();
        var rOac = advisor.handleOrderAlreadyCancelled(oac);
        assertEquals(HttpStatus.NOT_FOUND, rOac.getStatusCode());
        assertEquals(oac.getMessage(), Objects.requireNonNull(rOac.getBody()).get("Message"));

        // TrackNotCreateException
        var tnc = new TrackNotCreateException("x");
        var rTnc = advisor.handleSmsNotSend(tnc);
        assertEquals(HttpStatus.NOT_FOUND, rTnc.getStatusCode());
        assertEquals(tnc.getMessage(), Objects.requireNonNull(rTnc.getBody()).get("Message"));

        // NoOrderFoundException
        var nof = new NoOrderFoundException();
        var rNof = advisor.handleOrderNotFound(nof);
        assertEquals(HttpStatus.NOT_FOUND, rNof.getStatusCode());
        assertEquals(nof.getMessage(), Objects.requireNonNull(rNof.getBody()).get("Message"));
    }

    @Test
    void handleMethodArgumentNotValidException() throws Exception {
        BeanPropertyBindingResult binding = new BeanPropertyBindingResult(this, "obj");
        binding.addError(new FieldError("obj", "fieldA", "msgA"));
        binding.addError(new FieldError("obj", "fieldB", "msgB"));

        Method m = ControllerAdvisor.class
                .getDeclaredMethod("handleValidationErrors", MethodArgumentNotValidException.class);
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(
                new org.springframework.core.MethodParameter(m, 0), binding
        );

        var response = advisor.handleValidationErrors(ex);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, String> body = response.getBody();
        assert body != null;
        assertEquals("msgA", body.get("fieldA"));
        assertEquals("msgB", body.get("fieldB"));
    }
}