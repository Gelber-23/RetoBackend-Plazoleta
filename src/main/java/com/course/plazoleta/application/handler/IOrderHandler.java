package com.course.plazoleta.application.handler;

import com.course.plazoleta.application.dto.request.order.OrderCreateRequest;
import com.course.plazoleta.domain.model.Order;

public interface IOrderHandler {

    void createOrder (OrderCreateRequest orderCreateRequest);
}
