package com.course.plazoleta.infraestructure.input.res;

import com.course.plazoleta.application.dto.request.DishRequest;
import com.course.plazoleta.application.dto.request.DishUpdateRequest;
import com.course.plazoleta.application.dto.response.DishListResponse;
import com.course.plazoleta.application.dto.response.DishResponse;
import com.course.plazoleta.application.handler.IDishHandler;
import com.course.plazoleta.domain.model.Dish;
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
    @Test
    void getAllDishesByRestaurantByCategory_shouldReturnPageModel() {
        int page = 0;
        int pageSize = 10;
        int idRestaurant = 1;
        int idCategory = 2;

        DishListResponse response = new DishListResponse();
        List<DishListResponse> responseList = Collections.singletonList(response);
        PageModel<DishListResponse> pageModel = new PageModel<>(responseList, page, pageSize, 1L, 1);

        when(dishHandler.getAllDishesByRestaurantByCategory(page, pageSize, idRestaurant, idCategory))
                .thenReturn(pageModel);

        ResponseEntity<PageModel<DishListResponse>> result = dishRestController
                .getAllDishesByRestaurantByCategory(page, pageSize, idRestaurant, idCategory);

        verify(dishHandler).getAllDishesByRestaurantByCategory(page, pageSize, idRestaurant, idCategory);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(pageModel, result.getBody());
    }
}