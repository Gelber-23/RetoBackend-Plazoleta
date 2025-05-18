package com.course.plazoleta.domain.usecase;

import com.course.plazoleta.domain.model.Dish;
import com.course.plazoleta.domain.spi.IDishPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class DishUseCaseTest {

    @Mock
    private IDishPersistencePort dishPersistencePort;

    @InjectMocks
    private DishUseCase dishUseCase;

    private Dish dish;

    @BeforeEach
    void setUp() {
        dish = new Dish(1L, "Burger", 3, "Beef Burger", 2000, "http://image.com/burger.png", 7, false);

    }

    @Test
    void shouldSaveDish() {
        dishUseCase.saveDish(dish);
        verify(dishPersistencePort).saveDish(dish);
    }

    @Test
    void shouldReturnDishById() {
        when(dishPersistencePort.getDishById(1L)).thenReturn(dish);

        Dish result = dishPersistencePort.getDishById(1L);

        assertNotNull(result);
        assertEquals(dish.getId(), result.getId());
        verify(dishPersistencePort).getDishById(1L);
    }

    @Test
    void shouldReturnAllDishes() {
        List<Dish> dishList = Collections.singletonList(dish);

        when(dishPersistencePort.getAllDishes()).thenReturn(dishList);

        List<Dish> result = dishUseCase.getAllDishes();

        assertEquals(1, result.size());
        assertEquals("Burger", result.getFirst().getName());
        verify(dishPersistencePort).getAllDishes();
    }

    @Test
    void shouldUpdateDish() {
        dishUseCase.updateDish(dish);
        verify(dishPersistencePort).updateDish(dish);
    }
}