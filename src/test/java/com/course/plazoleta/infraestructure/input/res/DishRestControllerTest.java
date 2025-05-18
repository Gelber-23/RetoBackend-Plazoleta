package com.course.plazoleta.infraestructure.input.res;

import com.course.plazoleta.application.dto.request.DishRequest;
import com.course.plazoleta.application.dto.response.DishResponse;
import com.course.plazoleta.application.handler.IDishHandler;
import com.course.plazoleta.application.mapper.response.IDishResponseMapper;
import com.course.plazoleta.domain.model.Dish;
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
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DishRestControllerTest {
    @Mock
    private IDishHandler dishHandler;
    @Mock
    private IDishResponseMapper dishResponseMapper;

    @InjectMocks
    private DishRestController dishRestController;


    private Dish dish;
    @BeforeEach
    void setUp() {

        dish = new Dish();
        dish.setId(1L);
        dish.setName("Dish");
        dish.setDescription("Dish New");
        dish.setIdCategory(1);
        dish.setActive(true);
        dish.setPrice(10);
        dish.setIdRestaurant(1);
        dish.setUrlImage("https://logo.com/logo.png");
    }

    @Test
    void shouldSaveDishSuccessfully() {
        DishRequest request = new DishRequest();


        request.setName("Dish");
        request.setDescription("Dish New");
        request.setIdCategory(1);
        request.setPrice(10);
        request.setIdRestaurant(1);
        request.setUrlImage("https://logo.com/logo.png");

        ResponseEntity<Void> response = dishRestController.saveDish(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(dishHandler).saveDish(request);
    }

    @Test
    void shouldGetDishByIdSuccessfully() {

        DishResponse dishResponse =  dishResponseMapper.toResponse(dish);

        when(dishHandler.getDishById(dish.getId())).thenReturn(dishResponse);

        ResponseEntity<DishResponse> response = dishRestController.getDishById(dish.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dishResponse, response.getBody());
        verify(dishHandler).getDishById(dish.getId());
    }

    @Test
    void shouldGetAllDishesSuccessfully() {
        List<DishResponse> responseList = Collections.singletonList(dishResponseMapper.toResponse(dish));

        when(dishHandler.getAllDishes()).thenReturn(responseList);

        ResponseEntity<List<DishResponse>> response = dishRestController.getAllDishes();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
        verify(dishHandler).getAllDishes();
    }
}