package com.course.plazoleta.infraestructure.input.res;

import com.course.plazoleta.application.dto.request.order.OrderCreateRequest;
import com.course.plazoleta.application.dto.response.order.OrderResponse;
import com.course.plazoleta.application.handler.IOrderHandler;
import com.course.plazoleta.domain.model.PageModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderRestControllerTest {


    @Mock
    private IOrderHandler orderHandler;
    @InjectMocks
    private OrderRestController orderRestController;
    @Test
    void createOrder_ShouldReturnCreatedStatus() {

        OrderCreateRequest request = new OrderCreateRequest();

        ResponseEntity<Void> response = orderRestController.createOrder(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNull(response.getBody());

        verify(orderHandler).createOrder(request);
    }

    @Test
    void getOrdersFilterByState_ShouldReturnPageModelOfOrderResponse() {

        int page = 0;
        int pageSize = 5;
        String state = "PENDING";

        List<OrderResponse> responseList = List.of(new OrderResponse());
        PageModel<OrderResponse> expected = new PageModel<>(responseList, page, pageSize, 1L, 1);

        when(orderHandler.getOrdersFilterByState(page, pageSize, state)).thenReturn(expected);


        ResponseEntity<PageModel<OrderResponse>> response = orderRestController.getOrdersFilterByState(page, pageSize, state);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
        verify(orderHandler).getOrdersFilterByState(page, pageSize, state);
    }

    @Test
    void takeOrder_shouldReturnOk() {
        Long idOrder = 1L;
        OrderResponse expectedResponse = new OrderResponse();
        when(orderHandler.takeOrder(idOrder)).thenReturn(expectedResponse);

        ResponseEntity<OrderResponse> response = orderRestController.takeOrder(idOrder);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
        verify(orderHandler).takeOrder(idOrder);
    }

    @Test
    void completeOrderAndNotify_shouldReturnOk() {
        Long idOrder = 2L;
        OrderResponse expectedResponse = new OrderResponse();
        when(orderHandler.completeOrderAndNotify(idOrder)).thenReturn(expectedResponse);

        ResponseEntity<OrderResponse> response = orderRestController.completeOrderAndNotify(idOrder);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
        verify(orderHandler).completeOrderAndNotify(idOrder);
    }

    @Test
    void deliverOrder_shouldReturnOk() {
        Long idOrder = 3L;
        String pin = "1234";
        OrderResponse expectedResponse = new OrderResponse();
        when(orderHandler.deliverOrder(idOrder, pin)).thenReturn(expectedResponse);

        ResponseEntity<OrderResponse> response = orderRestController.deliverOrder(idOrder, pin);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
        verify(orderHandler).deliverOrder(idOrder, pin);
    }

    @Test
    void cancelOrder_shouldReturnOk() {
        Long idOrder = 4L;
        OrderResponse expectedResponse = new OrderResponse();
        when(orderHandler.cancelOrder(idOrder)).thenReturn(expectedResponse);

        ResponseEntity<OrderResponse> response = orderRestController.cancelOrder(idOrder);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
        verify(orderHandler).cancelOrder(idOrder);
    }
}