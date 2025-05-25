package com.course.plazoleta.application.handler.impl;

import com.course.plazoleta.application.dto.request.order.OrderCreateRequest;
import com.course.plazoleta.application.handler.IOrderHandler;
import com.course.plazoleta.application.mapper.request.IOrderRequestMapper;
import com.course.plazoleta.domain.api.IOrderServicePort;
import com.course.plazoleta.domain.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderHandler implements IOrderHandler {

    private final IOrderServicePort orderServicePort;
    private final IOrderRequestMapper orderRequestMapper;
    @Override
    public void createOrder(OrderCreateRequest orderCreateRequest) {
        orderServicePort.createOrder(orderRequestMapper.toOrder(orderCreateRequest));
    }
}
