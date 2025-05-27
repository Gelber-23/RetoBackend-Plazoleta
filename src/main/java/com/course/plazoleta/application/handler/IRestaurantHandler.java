package com.course.plazoleta.application.handler;

import com.course.plazoleta.application.dto.request.RestaurantRequest;
import com.course.plazoleta.application.dto.response.RestaurantCompleteResponse;
import com.course.plazoleta.application.dto.response.RestaurantResponse;
import com.course.plazoleta.domain.model.PageModel;

public interface IRestaurantHandler {

    void saveRestaurant(RestaurantRequest restaurantRequest);

    RestaurantCompleteResponse getRestaurantById(long id);

    PageModel<RestaurantResponse> getAllRestaurants(Integer page , Integer pageSize, String fieldToSort);

    void deleteRestaurantById(long id);
}
