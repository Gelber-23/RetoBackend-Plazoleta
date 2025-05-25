package com.course.plazoleta.domain.spi;

import com.course.plazoleta.domain.model.Order;

import java.util.List;

public interface IOrderPersistencePort {
    void createOrder (Order order);

    boolean clientHaveOrderActive (Long idClient, List<String> states);
}
