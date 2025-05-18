package com.course.plazoleta.application.handler.impl;

import com.course.plazoleta.application.dto.request.DishRequest;
import com.course.plazoleta.application.dto.response.DishResponse;
import com.course.plazoleta.application.mapper.request.IDishRequestMapper;
import com.course.plazoleta.application.mapper.response.IDishResponseMapper;
import com.course.plazoleta.domain.api.IDishServicePort;
import com.course.plazoleta.domain.model.Dish;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DishHandlerTest {

    @Mock
    IDishServicePort dishServicePort;
    @Mock
    IDishRequestMapper dishRequestMapper;
    @Mock
    IDishResponseMapper dishResponseMapper;
    @InjectMocks
    DishHandler dishHandler;

    @Test
    void saveDish_delegatesToService() {
        DishRequest request = new DishRequest();
        Dish dish = new Dish();
        when(dishRequestMapper.toDish(request)).thenReturn(dish);

        dishHandler.saveDish(request);

        verify(dishRequestMapper).toDish(request);
        verify(dishServicePort).saveDish(dish);
    }

    @Test
    void getDishById_returnsMappedResponse() {
        Long id = 1L;
        Dish dish = new Dish();
        DishResponse resp = new DishResponse();
        when(dishServicePort.getDishById(id)).thenReturn(dish);
        when(dishResponseMapper.toResponse(dish)).thenReturn(resp);

        DishResponse result = dishHandler.getDishById(id);

        assertSame(resp, result);
        verify(dishServicePort).getDishById(id);
        verify(dishResponseMapper).toResponse(dish);
    }

    @Test
    void getAllDishes_returnsMappedList() {
        Dish dish = new Dish();
        DishResponse resp = new DishResponse();
        List<Dish> domains = Collections.singletonList(dish);
        List<DishResponse> dishResponseList = Collections.singletonList(resp);
        when(dishServicePort.getAllDishes()).thenReturn(domains);
        when(dishResponseMapper.toResponseList(domains)).thenReturn(dishResponseList);

        List<DishResponse> result = dishHandler.getAllDishes();

        assertEquals(dishResponseList, result);
        verify(dishServicePort).getAllDishes();
        verify(dishResponseMapper).toResponseList(domains);
    }
}