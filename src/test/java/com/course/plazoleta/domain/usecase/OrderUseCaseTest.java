package com.course.plazoleta.domain.usecase;


import com.course.plazoleta.domain.api.IUserServicePort;
import com.course.plazoleta.domain.api.IUtilsServicePort;
import com.course.plazoleta.domain.exception.*;
import com.course.plazoleta.domain.exception.usersexception.UserIsNotEmployeeRestaurantException;
import com.course.plazoleta.domain.exception.validation.OrderValidationException;
import com.course.plazoleta.domain.model.*;
import com.course.plazoleta.domain.model.feign.MessageSms;
import com.course.plazoleta.domain.model.feign.RoleDto;
import com.course.plazoleta.domain.model.feign.User;
import com.course.plazoleta.domain.spi.IOrderPersistencePort;
import com.course.plazoleta.domain.spi.feign.ITrackClientPort;
import com.course.plazoleta.domain.spi.feign.ITwilioClientPort;
import com.course.plazoleta.domain.utils.constants.ValuesConstants;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderUseCaseTest {
    @Mock
    private IOrderPersistencePort orderPersistencePort;
    @Mock
    private IUtilsServicePort utilsServicePort;
    @Mock
    private IUserServicePort userServicePort;
    @Mock
    private ITwilioClientPort twilioClientPort;
    @Mock
    private ITrackClientPort trackClientPort;

    @InjectMocks
    private OrderUseCase orderUseCase;

    private static final Long ID_ORDER = 1L;
    private static final Long ID_CLIENT = 10L;
    private static final Long ID_CHEF = 20L;
    private static final Long ID_RESTAURANT = 100L;

    private Order getBasicOrder(String state) {
        OrderDish dish = new OrderDish(1L, 2L,1L,1);
        Order order = new Order();
        order.setId(ID_ORDER);
        order.setDishes(Collections.singletonList(dish));
        order.setIdRestaurant(ID_RESTAURANT);
        order.setIdClient(ID_CLIENT);
        order.setIdChef(ID_CHEF);
        order.setState(state);
        return order;
    }

    private User getEmployeeUser() {
        RoleDto role = new RoleDto();
        role.setId(3); // EMPLOYEE
        User user = new User();
        user.setId(ID_CHEF);
        user.setRol(role);
        user.setIdRestaurant(ID_RESTAURANT);
        return user;
    }

    @Test
    void createOrderSuccess() {
        Order order = getBasicOrder(null);
        when(utilsServicePort.getCurrentUserId()).thenReturn(ID_CLIENT);
        when(orderPersistencePort.clientHaveOrderActive(eq(ID_CLIENT), anyList())).thenReturn(false);

        orderUseCase.createOrder(order);

        verify(orderPersistencePort).createOrder(any(Order.class));
    }

    @Test
    void createOrderWithActiveOrderThrowsException() {
        Order order = getBasicOrder(null);
        when(utilsServicePort.getCurrentUserId()).thenReturn(ID_CLIENT);
        when(orderPersistencePort.clientHaveOrderActive(eq(ID_CLIENT), anyList())).thenReturn(true);

        assertThrows(ClientHaveOrderActiveException.class, () -> orderUseCase.createOrder(order));
    }

    @Test
    void getOrdersFilterByStateSuccess() {
        User employee = getEmployeeUser();
        when(utilsServicePort.getCurrentUserId()).thenReturn(ID_CHEF);
        when(userServicePort.getUserById(ID_CHEF)).thenReturn(employee);
        PageModel<Order> pageModel = new PageModel<>(Collections.emptyList() ,1,1,1,1);
        when(orderPersistencePort.getOrdersFilterByState(anyInt(), anyInt(), anyString(), eq(ID_RESTAURANT)))
                .thenReturn(pageModel);

        PageModel<Order> result = orderUseCase.getOrdersFilterByState(0, 10, ValuesConstants.STATUS_PENDING_ORDER);

        assertEquals(pageModel, result);
    }

    @Test
    void takeOrderSuccess() {
        User employee = getEmployeeUser();
        Order order = getBasicOrder(ValuesConstants.STATUS_PENDING_ORDER);

        when(utilsServicePort.getCurrentUserId()).thenReturn(ID_CHEF);
        when(userServicePort.getUserById(ID_CHEF)).thenReturn(employee);
        when(orderPersistencePort.getOrderById(ID_ORDER)).thenReturn(order);
        when(orderPersistencePort.takeOrder(any(Order.class))).thenReturn(order);

        Order result = orderUseCase.takeOrder(ID_ORDER);

        assertEquals(ValuesConstants.STATUS_PREPARATION_ORDER , result.getState());
    }

    @Test
    void completeOrderAndNotifySuccess() {
        User employee = getEmployeeUser();
        Order order = getBasicOrder(ValuesConstants.STATUS_PREPARATION_ORDER );
        User client = new User();
        client.setPhone("1234567890");
        client.setName("John");

        when(utilsServicePort.getCurrentUserId()).thenReturn(ID_CHEF);
        when(userServicePort.getUserById(ID_CHEF)).thenReturn(employee);
        when(orderPersistencePort.getOrderById(ID_ORDER)).thenReturn(order);
        when(userServicePort.getUserById(ID_CLIENT)).thenReturn(client);
        when(orderPersistencePort.takeOrder(any())).thenReturn(order);

        Order result = orderUseCase.completeOrderAndNotify(ID_ORDER);

        assertEquals(ValuesConstants.STATUS_READY_ORDER, result.getState());
        verify(twilioClientPort).sendMessageSms(any(MessageSms.class));
    }

    @Test
    void deliverOrderSuccess() {
        User employee = getEmployeeUser();
        Order order = getBasicOrder(ValuesConstants.STATUS_READY_ORDER);
        order.setPin("1234");

        when(utilsServicePort.getCurrentUserId()).thenReturn(ID_CHEF);
        when(userServicePort.getUserById(ID_CHEF)).thenReturn(employee);
        when(orderPersistencePort.getOrderById(ID_ORDER)).thenReturn(order);
        when(orderPersistencePort.takeOrder(any())).thenReturn(order);

        Order result = orderUseCase.deliverOrder(ID_ORDER, "1234");

        assertEquals(ValuesConstants.STATUS_DELIVERED_ORDER, result.getState());
    }

    @Test
    void cancelOrderSuccess() {
        Order order = getBasicOrder(ValuesConstants.STATUS_PENDING_ORDER);
        when(utilsServicePort.getCurrentUserId()).thenReturn(ID_CLIENT);
        when(orderPersistencePort.getOrderById(ID_ORDER)).thenReturn(order);
        when(orderPersistencePort.takeOrder(any())).thenReturn(order);

        Order result = orderUseCase.cancelOrder(ID_ORDER);

        assertEquals( ValuesConstants.STATUS_CANCELED_ORDER, result.getState());
    }

    @Test
    void createOrderInvalidDishQuantityThrows() {
        Order order = new Order();
        order.setIdRestaurant(ID_RESTAURANT);
        order.setDishes(Collections.singletonList(new OrderDish(1L, 0L,1L,-1))); // invalid
        assertThrows(OrderValidationException.class, () -> orderUseCase.createOrder(order));
    }

    @Test
    void cancelOrderNotClientThrows() {
        Order order = getBasicOrder(ValuesConstants.STATUS_PENDING_ORDER);
        order.setIdClient(999L);
        when(utilsServicePort.getCurrentUserId()).thenReturn(ID_CLIENT);
        when(orderPersistencePort.getOrderById(ID_ORDER)).thenReturn(order);
        assertThrows(NotTheClientThisOrderException.class, () -> orderUseCase.cancelOrder(ID_ORDER));
    }

}