package com.course.plazoleta.domain.usecase;

import com.course.plazoleta.domain.api.IUserServicePort;
import com.course.plazoleta.domain.exception.RestaurantValidationException;
import com.course.plazoleta.domain.exception.UserNotOwnerException;
import com.course.plazoleta.domain.model.PageModel;
import com.course.plazoleta.domain.model.Restaurant;
import com.course.plazoleta.domain.model.RoleDto;
import com.course.plazoleta.domain.model.User;
import com.course.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.course.plazoleta.domain.utils.constants.ValuesConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantUseCaseTest {
    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;
    @Mock
    private IUserServicePort userServicePort;
    @InjectMocks
    private RestaurantUseCase restaurantUseCase;


    @Test
    void saveRestaurant_ValidOwner_SavesRestaurant() {
        Restaurant restaurant = createValidRestaurant();
        User owner = new User(1L, "Test", "User", "123", "1234567890", null, "test@email.com", "pass",
                new RoleDto(ValuesConstants.ID_ROLE_OWNER, "OWNER", "Restaurant Owner"));


        when(userServicePort.getUserById(restaurant.getId_owner())).thenReturn(owner);

        restaurantUseCase.saveRestaurant(restaurant);

        verify(restaurantPersistencePort, times(1)).saveRestaurant(restaurant);
    }

    @Test
    void saveRestaurant_NotOwner_ThrowsUserNotOwnerException() {
        Restaurant restaurant = createValidRestaurant();
        User user = new User(1L, "Test", "User", "123", "1234567890", null, "test@email.com", "pass",
                new RoleDto(ValuesConstants.ID_ROLE_EMPLOYEE, "ADMIN", "Admin"));

        when(userServicePort.getUserById(restaurant.getId_owner())).thenReturn(user);

        assertThrows(UserNotOwnerException.class, () -> restaurantUseCase.saveRestaurant(restaurant));
    }

    @Test
    void validateRestaurant_InvalidFields_ThrowsException() {
        Restaurant restaurant = new Restaurant();

        RestaurantValidationException ex = assertThrows(RestaurantValidationException.class,
                () -> RestaurantUseCase.validateRestaurant(restaurant));

        assertFalse(ex.getErrors().isEmpty());
    }

    @Test
    void getRestaurantById_ReturnsRestaurant() {
        Restaurant restaurant = createValidRestaurant();
        when(restaurantPersistencePort.getRestaurantById(1L)).thenReturn(restaurant);

        Restaurant result = restaurantUseCase.getRestaurantById(1L);

        assertEquals(restaurant, result);
    }

    @Test
    void getAllRestaurants_ReturnsPage() {
        PageModel<Restaurant> page = new PageModel<>(List.of(createValidRestaurant()), 0, 10, 1, 1);
        when(restaurantPersistencePort.getAllRestaurants(0, 10, "name")).thenReturn(page);

        PageModel<Restaurant> result = restaurantUseCase.getAllRestaurants(0, 10, "name");

        assertEquals(1, result.getContent().size());
    }

    @Test
    void deleteRestaurantById_Deletes() {
        restaurantUseCase.deleteRestaurantById(1L);
        verify(restaurantPersistencePort).deleteRestaurantById(1L);
    }

    // Helper
    private Restaurant createValidRestaurant() {
        Restaurant r = new Restaurant();
        r.setName("My Restaurant");
        r.setAddress("123 Main St");
        r.setId_owner(1L);
        r.setPhone("12345678903");
        r.setUrlLogo("https://logo.com");
        r.setNit("1234567890");
        return r;
    }
}