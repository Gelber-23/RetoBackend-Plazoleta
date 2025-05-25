package com.course.plazoleta.domain.api;

import com.course.plazoleta.domain.model.Order;

public interface IOrderServicePort {
    void createOrder (Order order);
}
