package com.course.plazoleta.infraestructure.input.res;

import com.course.plazoleta.application.dto.request.RestaurantRequest;
import com.course.plazoleta.application.dto.response.RestaurantResponse;
import com.course.plazoleta.application.handler.IRestaurantHandler;
import com.course.plazoleta.domain.model.PageModel;
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
    @InjectMocks
    private RestaurantRestController restaurantRestController;
    @Test
    void saveRestaurant_shouldReturnCreated() {

        RestaurantRequest request = new RestaurantRequest();


        ResponseEntity<Void> response = restaurantRestController.saveRestaurant(request);


        verify(restaurantHandler).saveRestaurant(request);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void getRestaurantById_shouldReturnRestaurant() {

        long id = 1L;
        RestaurantResponse expectedResponse = new RestaurantResponse();
        when(restaurantHandler.getRestaurantById(id)).thenReturn(expectedResponse);


        ResponseEntity<RestaurantResponse> response = restaurantRestController.getRestaurantById(id);


        verify(restaurantHandler).getRestaurantById(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void getAllRestaurants_shouldReturnPageModel() {

        int page = 0;
        int pageSize = 10;
        String field = "name";

        RestaurantResponse responseItem = new RestaurantResponse();
        List<RestaurantResponse> content = Collections.singletonList(responseItem);
        PageModel<RestaurantResponse> expectedPage = new PageModel<>(
                content,
                page,
                pageSize,
                1L,
                1
        );

        when(restaurantHandler.getAllRestaurants(page, pageSize, field)).thenReturn(expectedPage);


        ResponseEntity<PageModel<RestaurantResponse>> response = restaurantRestController.getAllRestaurants(page, pageSize, field);


        verify(restaurantHandler).getAllRestaurants(page, pageSize, field);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedPage, response.getBody());
    }

    @Test
    void deleteRestaurantById_shouldReturnOk() {

        long id = 1L;


        ResponseEntity<Void> response = restaurantRestController.deleteRestaurantById(id);

        verify(restaurantHandler).deleteRestaurantById(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}