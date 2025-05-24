package com.course.plazoleta.application.handler.impl;

import com.course.plazoleta.application.dto.request.DishRequest;
import com.course.plazoleta.application.dto.request.DishUpdateRequest;
import com.course.plazoleta.application.dto.response.DishListResponse;
import com.course.plazoleta.application.dto.response.DishResponse;
import com.course.plazoleta.application.mapper.request.IDishRequestMapper;
import com.course.plazoleta.application.mapper.request.IDishUpdateRequestMapper;
import com.course.plazoleta.application.mapper.response.IDishResponseMapper;
import com.course.plazoleta.domain.api.IDishServicePort;
import com.course.plazoleta.domain.model.Dish;
import com.course.plazoleta.domain.model.PageModel;
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
    private IDishServicePort dishServicePort;
    @Mock private IDishRequestMapper dishRequestMapper;
    @Mock private IDishUpdateRequestMapper dishUpdateRequestMapper;
    @Mock private IDishResponseMapper dishResponseMapper;
    @InjectMocks
    private DishHandler dishHandler;



    @Test
    void saveDish_shouldCallServiceWithMappedDish() {
        DishRequest dishRequest = new DishRequest();
        Dish mappedDish = new Dish();

        when(dishRequestMapper.toDish(dishRequest)).thenReturn(mappedDish);

        dishHandler.saveDish(dishRequest);

        verify(dishRequestMapper).toDish(dishRequest);
        verify(dishServicePort).saveDish(mappedDish);
    }

    @Test
    void getDishById_shouldReturnMappedResponse() {
        long id = 1L;
        Dish dish = new Dish();
        DishResponse response = new DishResponse();

        when(dishServicePort.getDishById(id)).thenReturn(dish);
        when(dishResponseMapper.toResponse(dish)).thenReturn(response);

        DishResponse result = dishHandler.getDishById(id);

        assertEquals(response, result);
        verify(dishServicePort).getDishById(id);
        verify(dishResponseMapper).toResponse(dish);
    }

    @Test
    void getAllDishes_shouldReturnMappedList() {
        List<Dish> dishes = Collections.singletonList(new Dish());
        List<DishResponse> responses = Collections.singletonList(new DishResponse());

        when(dishServicePort.getAllDishes()).thenReturn(dishes);
        when(dishResponseMapper.toResponseList(dishes)).thenReturn(responses);

        List<DishResponse> result = dishHandler.getAllDishes();

        assertEquals(responses, result);
        verify(dishServicePort).getAllDishes();
        verify(dishResponseMapper).toResponseList(dishes);
    }

    @Test
    void updateDish_shouldCallServiceWithMappedDish() {
        DishUpdateRequest updateRequest = new DishUpdateRequest();
        Dish mappedDish = new Dish();

        when(dishUpdateRequestMapper.toDish(updateRequest)).thenReturn(mappedDish);

        dishHandler.updateDish(updateRequest);

        verify(dishUpdateRequestMapper).toDish(updateRequest);
        verify(dishServicePort).updateDish(mappedDish);
    }

    @Test
    void changeStateDish_shouldCallService() {
        Dish dish = new Dish();
        dishHandler.changeStateDish(dish);
        verify(dishServicePort).changeStateDish(dish);
    }

    @Test
    void getAllDishesByRestaurantByCategory_shouldReturnMappedPageModel() {
        int page = 0;
        int pageSize = 10;
        int idRestaurant = 1;
        int idCategory = 2;

        Dish dish = new Dish();
        List<Dish> dishList = Collections.singletonList(dish);
        PageModel<Dish> dishPageModel = new PageModel<>(dishList, page, pageSize, 1L, 1);

        DishListResponse dishListResponse = new DishListResponse();
        List<DishListResponse> responseList = Collections.singletonList(dishListResponse);
        PageModel<DishListResponse> expectedResponse = new PageModel<>(responseList, page, pageSize, 1L, 1);

        when(dishServicePort.getAllDishesByRestaurantByCategory(page, pageSize, idRestaurant, idCategory))
                .thenReturn(dishPageModel);
        when(dishResponseMapper.toResponsePageModelList(dishPageModel))
                .thenReturn(expectedResponse);

        PageModel<DishListResponse> result = dishHandler.getAllDishesByRestaurantByCategory(page, pageSize, idRestaurant, idCategory);

        verify(dishServicePort).getAllDishesByRestaurantByCategory(page, pageSize, idRestaurant, idCategory);
        verify(dishResponseMapper).toResponsePageModelList(dishPageModel);
        assertEquals(expectedResponse, result);
    }
}