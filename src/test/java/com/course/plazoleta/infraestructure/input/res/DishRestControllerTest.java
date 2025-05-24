package com.course.plazoleta.infraestructure.input.res;

import com.course.plazoleta.application.dto.request.DishRequest;
import com.course.plazoleta.application.dto.request.DishUpdateRequest;
import com.course.plazoleta.application.dto.response.DishResponse;
import com.course.plazoleta.application.handler.IDishHandler;
import com.course.plazoleta.domain.model.Dish;
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
class DishRestControllerTest {

    @Mock
    private IDishHandler dishHandler;
    @InjectMocks
    private DishRestController dishRestController;
    @Test
    void saveDish_shouldReturnCreated() {
        DishRequest request = new DishRequest();
        dishRestController.saveDish(request);
        verify(dishHandler).saveDish(request);
    }

    @Test
    void getDishById_shouldReturnDish() {
        Long id = 1L;
        DishResponse expected = new DishResponse();
        when(dishHandler.getDishById(id)).thenReturn(expected);
        ResponseEntity<DishResponse> response = dishRestController.getDishById(id);
        verify(dishHandler).getDishById(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
    }

    @Test
    void getAllDishes_shouldReturnList() {
        List<DishResponse> expectedList = Collections.singletonList(new DishResponse());
        when(dishHandler.getAllDishes()).thenReturn(expectedList);
        ResponseEntity<List<DishResponse>> response = dishRestController.getAllDishes();
        verify(dishHandler).getAllDishes();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedList, response.getBody());
    }

    @Test
    void updateDish_shouldReturnOk() {
        DishUpdateRequest request = new DishUpdateRequest();
        ResponseEntity<Void> response = dishRestController.updateDish(request);
        verify(dishHandler).updateDish(request);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void changeStateDish_shouldReturnOk() {
        Dish id = new Dish();
        ResponseEntity<Void> response = dishRestController.changeStateDish(id);
        verify(dishHandler).changeStateDish(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}