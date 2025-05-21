package com.course.plazoleta.domain.api;

import com.course.plazoleta.domain.model.Restaurant;

import java.util.List;

public interface IRestaurantServicePort {
    void saveRestaurant(Restaurant restaurant);

    Restaurant getRestaurantById(long id);

    List<Restaurant> getAllRestaurants();

    void deleteRestaurantById(long id);
}
