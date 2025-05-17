package com.course.plazoleta.domain.usecase;

import com.course.plazoleta.domain.api.IRestaurantServicePort;
import com.course.plazoleta.domain.model.Restaurant;
import com.course.plazoleta.domain.spi.IRestaurantPersistencePort;

import java.util.List;

public class RestaurantUseCase implements IRestaurantServicePort {
    private final IRestaurantPersistencePort restaurantPersistencePort;

    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort) {
        this.restaurantPersistencePort = restaurantPersistencePort;
    }

    @Override
    public void saveRestaurant(Restaurant restaurant) {
        restaurantPersistencePort.saveRestaurant(restaurant);
    }

    @Override
    public Restaurant getRestaurantById(int id) {
        return restaurantPersistencePort.getRestaurantById(id);
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantPersistencePort.getAllRestaurants();
    }

    @Override
    public void deleteRestaurantById(int id) {
        restaurantPersistencePort.deleteRestaurantById(id);
    }
}
