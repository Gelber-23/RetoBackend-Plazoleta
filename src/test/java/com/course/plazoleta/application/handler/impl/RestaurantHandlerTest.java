package com.course.plazoleta.application.handler.impl;

import com.course.plazoleta.application.dto.request.RestaurantRequest;
import com.course.plazoleta.application.dto.response.RestaurantResponse;
import com.course.plazoleta.application.mapper.request.IRestaurantRequestMapper;
import com.course.plazoleta.application.mapper.response.IRestaurantResponseMapper;
import com.course.plazoleta.domain.api.IRestaurantServicePort;
import com.course.plazoleta.domain.model.Restaurant;
import org.junit.jupiter.api.BeforeEach;
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
class RestaurantHandlerTest {

    @Mock
    private IRestaurantServicePort restaurantServicePort;

    @Mock
    private IRestaurantRequestMapper restaurantRequestMapper;

    @Mock
    private IRestaurantResponseMapper restaurantResponseMapper;

    @InjectMocks
    private RestaurantHandler restaurantHandler;

    private Restaurant restaurant;
    private RestaurantRequest restaurantRequest;
    private RestaurantResponse restaurantResponse;

    @BeforeEach
    void setUp() {
        restaurantRequest = new RestaurantRequest();
        restaurantRequest.setName("Restaurant");
        restaurantRequest.setAddress("Address");
        restaurantRequest.setId_owner(1);
        restaurantRequest.setPhone("+1234567890");
        restaurantRequest.setUrlLogo("https://logo.com");
        restaurantRequest.setNit("123456789");

        restaurant = new Restaurant(1, "Restaurant", "Address", 1, "+1234567890", "https://logo.com", "123456789");

        restaurantResponse = new RestaurantResponse();
        restaurantResponse.setName("Restaurant");
        restaurantResponse.setUrlLogo("https://logo.com");
    }

    @Test
    void shouldSaveRestaurant() {
        when(restaurantRequestMapper.toRestaurant(restaurantRequest)).thenReturn(restaurant);

        restaurantHandler.saveRestaurant(restaurantRequest);

        verify(restaurantRequestMapper).toRestaurant(restaurantRequest);
        verify(restaurantServicePort).saveRestaurant(restaurant);
    }

    @Test
    void shouldGetRestaurantById() {
        int id = 1;

        when(restaurantServicePort.getRestaurantById(id)).thenReturn(restaurant);
        when(restaurantResponseMapper.toResponse(restaurant)).thenReturn(restaurantResponse);

        RestaurantResponse result = restaurantHandler.getRestaurantById(id);

        assertNotNull(result);
        assertEquals("Restaurant", result.getName());
        verify(restaurantServicePort).getRestaurantById(id);
        verify(restaurantResponseMapper).toResponse(restaurant);
    }

    @Test
    void shouldGetAllRestaurants() {
        List<Restaurant> restaurantList = Collections.singletonList(restaurant);
        List<RestaurantResponse> responseList = Collections.singletonList(restaurantResponse);

        when(restaurantServicePort.getAllRestaurants()).thenReturn(restaurantList);
        when(restaurantResponseMapper.toResponseList(restaurantList)).thenReturn(responseList);

        List<RestaurantResponse> result = restaurantHandler.getAllRestaurants();

        assertEquals(1, result.size());
        assertEquals("Restaurant", result.get(0).getName());
        verify(restaurantServicePort).getAllRestaurants();
        verify(restaurantResponseMapper).toResponseList(restaurantList);
    }

    @Test
    void shouldDeleteRestaurantById() {
        int id = 1;

        restaurantHandler.deleteRestaurantById(id);

        verify(restaurantServicePort).deleteRestaurantById(id);
    }
}