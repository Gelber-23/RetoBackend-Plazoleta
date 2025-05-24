package com.course.plazoleta.application.handler.impl;

import com.course.plazoleta.application.dto.request.RestaurantRequest;
import com.course.plazoleta.application.dto.response.RestaurantResponse;
import com.course.plazoleta.application.mapper.request.IRestaurantRequestMapper;
import com.course.plazoleta.application.mapper.response.IRestaurantResponseMapper;
import com.course.plazoleta.domain.api.IRestaurantServicePort;
import com.course.plazoleta.domain.model.PageModel;
import com.course.plazoleta.domain.model.Restaurant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantHandlerTest {
    @Mock private IRestaurantServicePort restaurantServicePort;
    @Mock private IRestaurantRequestMapper restaurantRequestMapper;
    @Mock
    private IRestaurantResponseMapper restaurantResponseMapper;
    @InjectMocks
    private RestaurantHandler restaurantHandler;


    @Test
    void saveRestaurant_callsServiceWithMappedObject() {
        RestaurantRequest request = new RestaurantRequest();
        Restaurant mappedRestaurant = new Restaurant();

        when(restaurantRequestMapper.toRestaurant(request)).thenReturn(mappedRestaurant);

        restaurantHandler.saveRestaurant(request);

        verify(restaurantRequestMapper).toRestaurant(request);
        verify(restaurantServicePort).saveRestaurant(mappedRestaurant);
    }

    @Test
    void getRestaurantById_returnsMappedResponse() {
        long id = 1L;
        Restaurant restaurant = new Restaurant();
        RestaurantResponse expectedResponse = new RestaurantResponse();

        when(restaurantServicePort.getRestaurantById(id)).thenReturn(restaurant);
        when(restaurantResponseMapper.toResponse(restaurant)).thenReturn(expectedResponse);

        RestaurantResponse result = restaurantHandler.getRestaurantById(id);

        assertEquals(expectedResponse, result);
        verify(restaurantServicePort).getRestaurantById(id);
        verify(restaurantResponseMapper).toResponse(restaurant);
    }

    @Test
    void getAllRestaurants_returnsPageModelMapped() {
        PageModel<Restaurant> restaurantPage = new PageModel<>(List.of(new Restaurant()), 0, 10, 1, 1);
        PageModel<RestaurantResponse> responsePage = new PageModel<>(List.of(new RestaurantResponse()), 0, 10, 1, 1);

        when(restaurantServicePort.getAllRestaurants(0, 10, "name")).thenReturn(restaurantPage);
        when(restaurantResponseMapper.toResponseList(restaurantPage)).thenReturn(responsePage);

        PageModel<RestaurantResponse> result = restaurantHandler.getAllRestaurants(0, 10, "name");

        assertEquals(responsePage, result);
        verify(restaurantServicePort).getAllRestaurants(0, 10, "name");
        verify(restaurantResponseMapper).toResponseList(restaurantPage);
    }

    @Test
    void deleteRestaurantById_callsService() {
        long id = 5L;
        restaurantHandler.deleteRestaurantById(id);
        verify(restaurantServicePort).deleteRestaurantById(id);
    }

}