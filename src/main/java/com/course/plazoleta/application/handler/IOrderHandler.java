package com.course.plazoleta.application.handler;

import com.course.plazoleta.application.dto.request.order.OrderCreateRequest;
import com.course.plazoleta.application.dto.response.order.OrderResponse;
import com.course.plazoleta.domain.model.PageModel;

public interface IOrderHandler {

    void createOrder (OrderCreateRequest orderCreateRequest);
    OrderResponse takeOrder (Long idOrder);
    OrderResponse completeOrderAndNotify (Long idOrder);
    OrderResponse deliverOrder (Long idOrder, String pin);
    OrderResponse cancelOrder (Long idOrder);
    PageModel<OrderResponse> getOrdersFilterByState(Integer page , Integer pageSize, String state);

}
