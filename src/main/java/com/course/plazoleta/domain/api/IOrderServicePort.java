package com.course.plazoleta.domain.api;

import com.course.plazoleta.domain.model.Order;
import com.course.plazoleta.domain.model.PageModel;


public interface IOrderServicePort {
    void createOrder (Order order);

    PageModel<Order> getOrdersFilterByState(Integer page , Integer pageSize, String state);
    Order takeOrder (Long idOrder);

    Order completeOrderAndNotify (Long idOrder);

    Order deliverOrder(Long idOrder, String pin);
}
