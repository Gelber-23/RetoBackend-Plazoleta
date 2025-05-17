package com.course.plazoleta.domain.usecase;

import com.course.plazoleta.domain.model.Restaurant;
import com.course.plazoleta.domain.spi.IRestaurantPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantUseCaseTest {
    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @InjectMocks
    private RestaurantUseCase restaurantUseCase;

    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant(1, "Restaurant", "Address", 100, "+1234567890", "https://logo.com/logo.png", "123456789");
    }

    @Test
    void shouldSaveRestaurant() {
        restaurantUseCase.saveRestaurant(restaurant);
        verify(restaurantPersistencePort).saveRestaurant(restaurant);
    }

    @Test
    void shouldReturnRestaurantById() {
        when(restaurantPersistencePort.getRestaurantById(1)).thenReturn(restaurant);

        Restaurant result = restaurantUseCase.getRestaurantById(1);

        assertNotNull(result);
        assertEquals(restaurant.getId(), result.getId());
        verify(restaurantPersistencePort).getRestaurantById(1);
    }

    @Test
    void shouldReturnAllRestaurants() {
        List<Restaurant> restaurantList = Arrays.asList(restaurant);

        when(restaurantPersistencePort.getAllRestaurants()).thenReturn(restaurantList);

        List<Restaurant> result = restaurantUseCase.getAllRestaurants();

        assertEquals(1, result.size());
        assertEquals("Restaurant", result.get(0).getName());
        verify(restaurantPersistencePort).getAllRestaurants();
    }

    @Test
    void shouldDeleteRestaurantById() {
        int id = 1;

        restaurantUseCase.deleteRestaurantById(id);

        verify(restaurantPersistencePort).deleteRestaurantById(id);
    }
}