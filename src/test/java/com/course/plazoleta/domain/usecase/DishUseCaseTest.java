package com.course.plazoleta.domain.usecase;

import com.course.plazoleta.domain.api.IUtilsServicePort;
import com.course.plazoleta.domain.exception.DishValidationException;
import com.course.plazoleta.domain.exception.NoDataFoundException;
import com.course.plazoleta.domain.exception.UserIsNotOwnerValidationException;
import com.course.plazoleta.domain.model.Dish;
import com.course.plazoleta.domain.model.PageModel;
import com.course.plazoleta.domain.model.Restaurant;
import com.course.plazoleta.domain.spi.IDishPersistencePort;
import com.course.plazoleta.domain.spi.IRestaurantPersistencePort;
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
class DishUseCaseTest {

    @Mock private IDishPersistencePort dishPersistencePort;
    @Mock private IRestaurantPersistencePort restaurantPersistencePort;
    @Mock private IUtilsServicePort utilsServicePort;
    @InjectMocks
    private DishUseCase dishUseCase;


    @Test
    void saveDish_validDish_savesSuccessfully() {
        Dish dish = createValidDish();
        Restaurant restaurant = new Restaurant();
        restaurant.setId_owner(1L);

        when(restaurantPersistencePort.getRestaurantById(dish.getIdRestaurant())).thenReturn(restaurant);
        when(utilsServicePort.getCurrentUserId()).thenReturn(1L);

        dishUseCase.saveDish(dish);

        assertTrue(dish.getActive());
        verify(dishPersistencePort).saveDish(dish);
    }

    @Test
    void saveDish_invalidDish_throwsValidationException() {
        Dish invalid = new Dish();
        DishValidationException ex = assertThrows(DishValidationException.class, () -> DishUseCase.validateDish(invalid));
        assertFalse(ex.getErrors().isEmpty());
    }

    @Test
    void saveDish_userNotOwner_throwsException() {
        Dish dish = createValidDish();
        Restaurant restaurant = new Restaurant();
        restaurant.setId_owner(2L); // distinto al currentUser

        when(restaurantPersistencePort.getRestaurantById(dish.getIdRestaurant())).thenReturn(restaurant);
        when(utilsServicePort.getCurrentUserId()).thenReturn(1L);

        assertThrows(UserIsNotOwnerValidationException.class, () -> dishUseCase.saveDish(dish));
    }

    @Test
    void getDishById_returnsDish() {
        Dish dish = createValidDish();
        when(dishPersistencePort.getDishById(1L)).thenReturn(dish);
        assertEquals(dish, dishUseCase.getDishById(1L));
    }

    @Test
    void getAllDishes_returnsList() {
        List<Dish> dishes = List.of(createValidDish());
        when(dishPersistencePort.getAllDishes()).thenReturn(dishes);
        assertEquals(dishes, dishUseCase.getAllDishes());
    }

    @Test
    void updateDish_valid_updateSuccess() {
        Dish input = createValidDish();
        Dish dbDish = createValidDish();
        dbDish.setDescription("Old");

        Restaurant restaurant = new Restaurant();
        restaurant.setId_owner(1L);

        when(dishPersistencePort.getDishById(input.getId())).thenReturn(dbDish);
        when(restaurantPersistencePort.getRestaurantById(dbDish.getIdRestaurant())).thenReturn(restaurant);
        when(utilsServicePort.getCurrentUserId()).thenReturn(1L);

        dishUseCase.updateDish(input);
        assertEquals(input.getDescription(), dbDish.getDescription());
        assertEquals(input.getPrice(), dbDish.getPrice());
        verify(dishPersistencePort).updateDish(dbDish);
    }

    @Test
    void updateDish_notFound_throwsException() {
        Dish dish = createValidDish();
        when(dishPersistencePort.getDishById(dish.getId())).thenReturn(null);
        assertThrows(NoDataFoundException.class, () -> dishUseCase.updateDish(dish));
    }

    @Test
    void changeStateDish_valid_changesState() {
        Dish dbDish = createValidDish();
        dbDish.setActive(true);

        Restaurant restaurant = new Restaurant();
        restaurant.setId_owner(1L);

        when(dishPersistencePort.getDishById(dbDish.getId())).thenReturn(dbDish);
        when(restaurantPersistencePort.getRestaurantById(dbDish.getIdRestaurant())).thenReturn(restaurant);
        when(utilsServicePort.getCurrentUserId()).thenReturn(1L);

        dishUseCase.changeStateDish(dbDish);
        assertFalse(dbDish.getActive());
        verify(dishPersistencePort).changeStateDish(dbDish);
    }

    @Test
    void changeStateDish_notFound_throwsException() {
        Dish dish = createValidDish();
        when(dishPersistencePort.getDishById(dish.getId())).thenReturn(null);
        assertThrows(NoDataFoundException.class, () -> dishUseCase.changeStateDish(dish));
    }
    @Test
    void getAllDishesByRestaurantByCategory_shouldReturnPageModel() {
        int page = 0;
        int pageSize = 10;
        int idRestaurant = 1;
        int idCategory = 2;

        Dish dish = new Dish();
        List<Dish> dishList = Collections.singletonList(dish);
        PageModel<Dish> expectedPageModel = new PageModel<>(dishList, page, pageSize, 1L, 1);

        when(dishPersistencePort.getAllDishesByRestaurantByCategory(page, pageSize, idRestaurant, idCategory))
                .thenReturn(expectedPageModel);

        PageModel<Dish> result = dishUseCase.getAllDishesByRestaurantByCategory(page, pageSize, idRestaurant, idCategory);

        verify(dishPersistencePort).getAllDishesByRestaurantByCategory(page, pageSize, idRestaurant, idCategory);
        assertEquals(expectedPageModel, result);
    }

    private Dish createValidDish() {
        Dish d = new Dish();
        d.setId(1L);
        d.setName("Pizza");
        d.setPrice(1000);
        d.setDescription("Delicious");
        d.setUrlImage("https://img.com");
        d.setIdCategory(1L);
        d.setIdRestaurant(1L);
        return d;
    }
}