package com.course.plazoleta.infraestructure.input.res;

import com.course.plazoleta.application.dto.request.RestaurantRequest;
import com.course.plazoleta.application.dto.response.RestaurantResponse;
import com.course.plazoleta.application.handler.IRestaurantHandler;
import com.course.plazoleta.application.mapper.response.IRestaurantResponseMapper;
import com.course.plazoleta.domain.model.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantRestControllerTest {
    @Mock
    private IRestaurantHandler restaurantHandler;
    @Mock
    private IRestaurantResponseMapper restaurantResponseMapper;

    @InjectMocks
    private RestaurantRestController restaurantRestController;


    private Restaurant restaurant;
    @BeforeEach
    void setUp() {

        restaurant = new Restaurant();
        restaurant.setId(1);
        restaurant.setName("Restaurant");
        restaurant.setAddress("Address");
        restaurant.setId_owner(100);
        restaurant.setPhone("+1234567890");
        restaurant.setUrlLogo("https://logo.com/logo.png");
        restaurant.setNit("123456789");

    }
    @Test
    void shouldSaveRestaurantSuccessfully() {
        RestaurantRequest request = new RestaurantRequest();

        request.setName("Restaurant");
        request.setAddress("Address");
        request.setId_owner(100);
        request.setPhone("+1234567890");
        request.setUrlLogo("https://logo.com/logo.png");
        request.setNit("123456789");

        ResponseEntity<Void> response = restaurantRestController.saveRestaurant(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(restaurantHandler).saveRestaurant(request);
    }

    @Test
    void shouldGetRestaurantByIdSuccessfully() {

        RestaurantResponse restaurantResponse =  restaurantResponseMapper.toResponse(restaurant);

        when(restaurantHandler.getRestaurantById(restaurant.getId())).thenReturn(restaurantResponse);

        ResponseEntity<RestaurantResponse> response = restaurantRestController.getRestaurantById(restaurant.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(restaurantResponse, response.getBody());
        verify(restaurantHandler).getRestaurantById(restaurant.getId());
    }

    @Test
    void shouldGetAllRestaurantsSuccessfully() {
        List<RestaurantResponse> responseList = Collections.singletonList(restaurantResponseMapper.toResponse(restaurant));

        when(restaurantHandler.getAllRestaurants()).thenReturn(responseList);

        ResponseEntity<List<RestaurantResponse>> response = restaurantRestController.getAllRestaurants();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(restaurantHandler).getAllRestaurants();
    }

    @Test
    void shouldDeleteRestaurantSuccessfully() {
        int restaurantId = 1;

        ResponseEntity<Void> response = restaurantRestController.deleteRestaurantById(restaurantId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(restaurantHandler).deleteRestaurantById(restaurantId);
    }

}