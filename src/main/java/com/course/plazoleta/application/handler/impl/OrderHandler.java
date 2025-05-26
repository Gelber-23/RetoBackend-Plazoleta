package com.course.plazoleta.application.handler.impl;

import com.course.plazoleta.application.dto.request.order.OrderCreateRequest;
import com.course.plazoleta.application.dto.response.order.OrderResponse;
import com.course.plazoleta.application.handler.IOrderHandler;
import com.course.plazoleta.application.mapper.request.IOrderRequestMapper;
import com.course.plazoleta.application.mapper.response.order.IOrderResponseMapper;
import com.course.plazoleta.domain.api.IOrderServicePort;
import com.course.plazoleta.domain.model.PageModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderHandler implements IOrderHandler {

    private final IOrderServicePort orderServicePort;
    private final IOrderRequestMapper orderRequestMapper;
    private final IOrderResponseMapper orderResponseMapper;
    @Override
    public void createOrder(OrderCreateRequest orderCreateRequest) {
        orderServicePort.createOrder(orderRequestMapper.toOrder(orderCreateRequest));
    }

    @Override
    public OrderResponse takeOrder(Long idOrder) {
        return orderResponseMapper.toResponse(orderServicePort.takeOrder(idOrder) );
    }

    @Override
    public OrderResponse completeOrderAndNotify(Long idOrder) {
        return orderResponseMapper.toResponse(orderServicePort.completeOrderAndNotify(idOrder) );
    }

    @Override
    public PageModel<OrderResponse> getOrdersFilterByState(Integer page, Integer pageSize, String state) {
        return orderResponseMapper.toResponsePageList(orderServicePort.getOrdersFilterByState(page,pageSize,state));
    }
}
