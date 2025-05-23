package com.course.plazoleta.application.handler;

import com.course.plazoleta.application.dto.request.RestaurantRequest;
import com.course.plazoleta.application.dto.response.RestaurantResponse;


import java.util.List;

public interface IRestaurantHandler {

    void saveRestaurant(RestaurantRequest restaurantRequest);

    RestaurantResponse getRestaurantById(long id);

    List<RestaurantResponse> getAllRestaurants();

    void deleteRestaurantById(long id);
}
