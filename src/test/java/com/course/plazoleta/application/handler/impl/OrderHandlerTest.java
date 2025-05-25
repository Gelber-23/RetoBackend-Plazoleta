package com.course.plazoleta.application.handler.impl;

import com.course.plazoleta.application.dto.request.order.OrderCreateRequest;
import com.course.plazoleta.application.dto.response.order.OrderResponse;
import com.course.plazoleta.application.mapper.request.IOrderRequestMapper;
import com.course.plazoleta.application.mapper.response.order.IOrderResponseMapper;
import com.course.plazoleta.domain.api.IOrderServicePort;
import com.course.plazoleta.domain.model.Order;
import com.course.plazoleta.domain.model.PageModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderHandlerTest {

    @Mock
    private IOrderServicePort orderServicePort;

    @Mock
    private IOrderRequestMapper orderRequestMapper;

    @Mock
    private IOrderResponseMapper orderResponseMapper;
    @InjectMocks
    private OrderHandler orderHandler;

    @Test
    void createOrder_ShouldCallServiceWithMappedOrder() {
        OrderCreateRequest request = new OrderCreateRequest();
        Order mappedOrder = new Order();

        when(orderRequestMapper.toOrder(request)).thenReturn(mappedOrder);

        orderHandler.createOrder(request);

        verify(orderRequestMapper).toOrder(request);
        verify(orderServicePort).createOrder(mappedOrder);
    }

    @Test
    void getOrdersFilterByState_ShouldReturnMappedPageModel() {
        int page = 0;
        int pageSize = 5;
        String state = "PENDING";

        PageModel<Order> servicePage = new PageModel<>(List.of(), page, pageSize, 0L, 0);
        PageModel<OrderResponse> expectedPage = new PageModel<>(List.of(), page, pageSize, 0L, 0);

        when(orderServicePort.getOrdersFilterByState(page, pageSize, state)).thenReturn(servicePage);
        when(orderResponseMapper.toResponsePageList(servicePage)).thenReturn(expectedPage);

        PageModel<OrderResponse> result = orderHandler.getOrdersFilterByState(page, pageSize, state);

        assertEquals(expectedPage, result);
        verify(orderServicePort).getOrdersFilterByState(page, pageSize, state);
        verify(orderResponseMapper).toResponsePageList(servicePage);
    }

}