package com.course.plazoleta.domain.spi;

import com.course.plazoleta.domain.model.Order;
import com.course.plazoleta.domain.model.PageModel;

import java.util.List;

public interface IOrderPersistencePort {
    void createOrder (Order order);
    PageModel<Order> getOrdersFilterByState(Integer page , Integer pageSize, String state, Long idRestaurant);
    boolean clientHaveOrderActive (Long idClient, List<String> states);
}
