package com.course.plazoleta.domain.usecase;


import com.course.plazoleta.domain.api.IUtilsServicePort;
import com.course.plazoleta.domain.exception.ClientHaveOrderActiveException;
import com.course.plazoleta.domain.exception.OrderValidationException;
import com.course.plazoleta.domain.model.Order;
import com.course.plazoleta.domain.model.OrderDish;
import com.course.plazoleta.domain.spi.IOrderPersistencePort;
import com.course.plazoleta.domain.utils.constants.ValuesConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderUseCaseTest {



    @Mock
    private IOrderPersistencePort orderPersistencePort;

    @Mock
    private IUtilsServicePort utilsServicePort;



    @InjectMocks
    private OrderUseCase orderUseCase;

    @Test
    void createOrder_ShouldSaveOrder_WhenOrderIsValidAndClientHasNoActiveOrders() {
        Order order = new Order();
        order.setIdRestaurant(1L);
        OrderDish orderDish = new OrderDish();
        orderDish.setIdDish(1L);
        orderDish.setQuantity(2);
        order.setDishes(Collections.singletonList(orderDish));

        long currentUserId = 100L;

        when(utilsServicePort.getCurrentUserId()).thenReturn(currentUserId);
        when(orderPersistencePort.clientHaveOrderActive(eq(currentUserId), anyList())).thenReturn(false);

        orderUseCase.createOrder(order);

        assertEquals(ValuesConstants.STATUS_PENDING_ORDER, order.getState());
        assertNotNull(order.getDate());
        assertEquals(currentUserId, order.getIdClient());
        assertEquals(0L, order.getIdChef());

        verify(orderPersistencePort).createOrder(order);
    }

    @Test
    void createOrder_ShouldThrowException_WhenClientHasActiveOrder() {
        Order order = new Order();
        order.setIdRestaurant(1L);
        OrderDish dish = new OrderDish();
        dish.setIdDish(1L);
        dish.setQuantity(1);
        order.setDishes(List.of(dish));

        long userId = 123L;

        when(utilsServicePort.getCurrentUserId()).thenReturn(userId);
        when(orderPersistencePort.clientHaveOrderActive(userId, List.of(
                ValuesConstants.STATUS_PENDING_ORDER,
                ValuesConstants.STATUS_PREPARATION_ORDER,
                ValuesConstants.STATUS_READY_ORDER
        ))).thenReturn(true);

        assertThrows(ClientHaveOrderActiveException.class, () -> orderUseCase.createOrder(order));
    }

    @Test
    void createOrder_ShouldThrowOrderValidationException_WhenOrderIsInvalid() {
        Order order = new Order();

        OrderValidationException exception = assertThrows(OrderValidationException.class, () ->
                orderUseCase.createOrder(order));

        assertFalse(exception.getErrors().isEmpty());
    }

}