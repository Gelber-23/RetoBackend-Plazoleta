package com.course.plazoleta.domain.usecase;


import com.course.plazoleta.domain.api.IUserServicePort;
import com.course.plazoleta.domain.api.IUtilsServicePort;
import com.course.plazoleta.domain.exception.*;
import com.course.plazoleta.domain.exception.usersexception.UserIsNotEmployeeRestaurantException;
import com.course.plazoleta.domain.exception.validation.OrderValidationException;
import com.course.plazoleta.domain.model.*;
import com.course.plazoleta.domain.model.feign.RoleDto;
import com.course.plazoleta.domain.model.feign.User;
import com.course.plazoleta.domain.spi.IOrderPersistencePort;
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

    @InjectMocks
    private OrderUseCase useCase;

    private User validEmployee;

    @BeforeEach
    void setUp() {
        validEmployee = new User();
        validEmployee.setId(42L);
        validEmployee.setRol(new RoleDto(ValuesConstants.ID_ROLE_EMPLOYEE ,"1","1"));
        validEmployee.setIdRestaurant(99L);
    }


    @Test
    void createOrder_successful() {

        Order o = new Order();
        o.setIdRestaurant(5L);
        OrderDish d = new OrderDish();
        d.setIdDish(7L);
        d.setQuantity(3);
        o.setDishes(List.of(d));

        when(utilsServicePort.getCurrentUserId()).thenReturn(123L);
        when(orderPersistencePort.clientHaveOrderActive(eq(123L), anyList())).thenReturn(false);


        useCase.createOrder(o);

        assertEquals(ValuesConstants.STATUS_PENDING_ORDER, o.getState());
        assertNotNull(o.getDate());
        assertEquals(123L, o.getIdClient());
        assertEquals(0L, o.getIdChef());
        verify(orderPersistencePort).createOrder(o);
    }

    @Test
    void createOrder_clientHasActiveOrder_throws() {

        Order o = new Order();
        o.setIdRestaurant(5L);
        OrderDish d = new OrderDish();
        d.setIdDish(7L);
        d.setQuantity(1);
        o.setDishes(List.of(d));

        when(utilsServicePort.getCurrentUserId()).thenReturn(321L);
        when(orderPersistencePort.clientHaveOrderActive(eq(321L), anyList())).thenReturn(true);


        assertThrows(ClientHaveOrderActiveException.class, () -> useCase.createOrder(o));
        verify(orderPersistencePort, never()).createOrder(any());
    }

    @Test
    void createOrder_invalidOrder_throwsValidationException() {

        Order o = new Order();


        OrderValidationException ex = assertThrows(OrderValidationException.class, () -> useCase.createOrder(o));
        assertFalse(ex.getErrors().isEmpty());
    }


    @Test
    void getOrdersFilterByState_successful() {

        PageModel<Order> pm = new PageModel<>(Collections.emptyList(), 0, 10, 0L, 0);
        when(utilsServicePort.getCurrentUserId()).thenReturn(42L);
        when(userServicePort.getUserById(42L)).thenReturn(validEmployee);
        when(orderPersistencePort.getOrdersFilterByState(1, 5, "P", 99L)).thenReturn(pm);


        PageModel<Order> result = useCase.getOrdersFilterByState(1, 5, "P");


        assertSame(pm, result);
        verify(orderPersistencePort).getOrdersFilterByState(1, 5, "P", 99L);
    }

    @Test
    void getOrdersFilterByState_notEmployee_throws() {

        User u = new User();
        u.setId(1L);
        u.setRol(new RoleDto(999,"",""));
        when(utilsServicePort.getCurrentUserId()).thenReturn(1L);
        when(userServicePort.getUserById(1L)).thenReturn(u);


        assertThrows(NotEmployeeUserException.class, () -> useCase.getOrdersFilterByState(0, 1, "X"));
    }

    @Test
    void getOrdersFilterByState_noRestaurantAssigned_throws() {

        User u = new User();
        u.setId(1L);
        u.setRol(new RoleDto(ValuesConstants.ID_ROLE_EMPLOYEE ,"" ,""));
        u.setIdRestaurant(0L);
        when(utilsServicePort.getCurrentUserId()).thenReturn(1L);
        when(userServicePort.getUserById(1L)).thenReturn(u);


        assertThrows(UserIsNotEmployeeRestaurantException.class,
                () -> useCase.getOrdersFilterByState(0, 1, "X"));
    }



    @Test
    void takeOrder_successful() {
        // Arrange
        Order o = new Order();
        o.setId(100L);
        o.setState(ValuesConstants.STATUS_PENDING_ORDER);

        when(utilsServicePort.getCurrentUserId()).thenReturn(42L);
        when(userServicePort.getUserById(42L)).thenReturn(validEmployee);
        when(orderPersistencePort.getOrderById(100L)).thenReturn(o);
        when(orderPersistencePort.takeOrder(any())).thenAnswer(inv -> inv.getArgument(0));


        Order updated = useCase.takeOrder(100L);


        assertEquals(ValuesConstants.STATUS_PREPARATION_ORDER, updated.getState());
        assertEquals(42L, updated.getIdChef());
        verify(orderPersistencePort).takeOrder(o);
    }

    @Test
    void takeOrder_notFound_throws() {

        when(utilsServicePort.getCurrentUserId()).thenReturn(42L);
        when(userServicePort.getUserById(42L)).thenReturn(validEmployee);
        when(orderPersistencePort.getOrderById(200L)).thenReturn(null);


        assertThrows(NoDataFoundException.class, () -> useCase.takeOrder(200L));
    }

    @Test
    void takeOrder_wrongState_throws() {

        Order o = new Order();
        o.setId(101L);
        o.setState(ValuesConstants.STATUS_READY_ORDER);

        when(utilsServicePort.getCurrentUserId()).thenReturn(42L);
        when(userServicePort.getUserById(42L)).thenReturn(validEmployee);
        when(orderPersistencePort.getOrderById(101L)).thenReturn(o);


        assertThrows(NoDataFoundException.class, () -> useCase.takeOrder(101L));
    }

}