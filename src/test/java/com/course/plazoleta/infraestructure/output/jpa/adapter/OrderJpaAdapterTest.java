package com.course.plazoleta.infraestructure.output.jpa.adapter;

import com.course.plazoleta.domain.exception.DishNotFoundException;
import com.course.plazoleta.domain.model.Order;
import com.course.plazoleta.domain.model.OrderDish;
import com.course.plazoleta.domain.model.PageModel;
import com.course.plazoleta.infraestructure.output.jpa.entity.DishEntity;
import com.course.plazoleta.infraestructure.output.jpa.entity.OrderDishEntity;
import com.course.plazoleta.infraestructure.output.jpa.entity.OrderEntity;
import com.course.plazoleta.infraestructure.output.jpa.mapper.IOrderDishEntityMapper;
import com.course.plazoleta.infraestructure.output.jpa.mapper.IOrderEntityMapper;
import com.course.plazoleta.infraestructure.output.jpa.repository.IDishRepository;
import com.course.plazoleta.infraestructure.output.jpa.repository.IOrderDishRepository;
import com.course.plazoleta.infraestructure.output.jpa.repository.IOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderJpaAdapterTest {
    @Mock
    private IOrderRepository orderRepository;

    @Mock
    private IOrderEntityMapper orderEntityMapper;

    @Mock
    private IDishRepository dishRepository;

    @Mock
    private IOrderDishRepository orderDishRepository;

    @Mock
    private IOrderDishEntityMapper orderDishEntityMapper;

    @InjectMocks
    private OrderJpaAdapter orderJpaAdapter;
    private Order order;
    private OrderEntity orderEntity;
    @BeforeEach
    void setUp() {
        order = new Order();
        order.setIdClient(1L);
        order.setIdRestaurant(2L);
        order.setState("PENDING");
        order.setDate(new Date());
        order.setIdChef(3L);
        order.setDishes(new ArrayList<>());

        orderEntity = new OrderEntity();
        orderEntity.setId(10L);
    }

    @Test
    void createOrder_shouldPersistOrderAndDishes() {

        order.setDishes(List.of(new OrderDish(1L, 2L,2L, 1)));

        OrderEntity savedOrderEntity = new OrderEntity();
        DishEntity dishEntity = new DishEntity();

        when(orderEntityMapper.toEntity(order)).thenReturn(savedOrderEntity);
        when(orderRepository.save(savedOrderEntity)).thenReturn(savedOrderEntity);
        when(dishRepository.findById(2L)).thenReturn(Optional.of(dishEntity));

        orderJpaAdapter.createOrder(order);

        verify(orderRepository).save(savedOrderEntity);
        verify(dishRepository).findById(2L);
        verify(orderDishRepository).saveAll(Mockito.anyList());
    }

    @Test
    void getOrdersFilterByState_shouldReturnPageModel() {
        int page = 0;
        int pageSize = 2;
        String state = "PENDING";
        Long idRestaurant = 1L;


        orderEntity.setId(1L);


        order.setId(1L);

        Page<OrderEntity> orderEntityPage = new PageImpl<>(List.of(orderEntity), PageRequest.of(page, pageSize), 1);

        when(orderRepository.findAllByStateAndIdRestaurant_Id(state, idRestaurant, PageRequest.of(page, pageSize, Sort.by(Sort.Direction.ASC, "id"))))
                .thenReturn(orderEntityPage);

        when(orderEntityMapper.toOrderModel(orderEntity)).thenReturn(order);

        OrderDishEntity orderDishEntity = new OrderDishEntity();
        orderDishEntity.setIdOrder(orderEntity);
        orderDishEntity.setIdDish(new DishEntity());

        OrderDish orderDish = new OrderDish();
        orderDish.setIdOrder(1L);

        when(orderDishRepository.findAllByIdOrder_IdIn(List.of(1L))).thenReturn(List.of(orderDishEntity));
        when(orderDishEntityMapper.toOrderModel(orderDishEntity)).thenReturn(orderDish);

        PageModel<Order> result = orderJpaAdapter.getOrdersFilterByState(page, pageSize, state, idRestaurant);

        assertEquals(1, result.getContent().size());
        assertEquals(1, result.getTotalElements());
    }
    @Test
    void createOrder_whenDishNotFound_throwsException() {
        OrderDish missing = new OrderDish();
        missing.setIdDish(99L);
        missing.setQuantity(1);
        order.setDishes(List.of(missing));

        when(orderEntityMapper.toEntity(order)).thenReturn(orderEntity);
        when(orderRepository.save(orderEntity)).thenReturn(orderEntity);
        when(dishRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(DishNotFoundException.class, () -> orderJpaAdapter.createOrder(order));

        verify(orderRepository).save(orderEntity);
        verify(orderDishRepository, never()).saveAll(anyList());
    }
    @Test
    void clientHaveOrderActive_shouldReturnTrue() {
        Long clientId = 1L;
        List<String> states = List.of("PENDING");

        when(orderRepository.existsByIdClientAndStateIn(clientId, states)).thenReturn(true);

        boolean result = orderJpaAdapter.clientHaveOrderActive(clientId, states);

        assertTrue(result);
    }
}