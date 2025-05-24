package com.course.plazoleta.domain.api;

import com.course.plazoleta.domain.model.PageModel;
import com.course.plazoleta.domain.model.Restaurant;


public interface IRestaurantServicePort {
    void saveRestaurant(Restaurant restaurant);

    Restaurant getRestaurantById(long id);

    PageModel<Restaurant> getAllRestaurants(Integer page , Integer pageSize, String fieldToSort);

    void deleteRestaurantById(long id);
}
