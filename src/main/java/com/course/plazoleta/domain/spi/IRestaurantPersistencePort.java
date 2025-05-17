package com.course.plazoleta.domain.spi;

import com.course.plazoleta.domain.model.Restaurant;

import java.util.List;

public interface IRestaurantPersistencePort {
    void saveRestaurant(Restaurant restaurant);

    Restaurant getRestaurantById(int id);

    List<Restaurant> getAllRestaurants();

    void deleteRestaurantById(int id);
}
