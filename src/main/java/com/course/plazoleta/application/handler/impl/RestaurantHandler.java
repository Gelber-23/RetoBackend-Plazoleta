package com.course.plazoleta.application.handler.impl;

import com.course.plazoleta.application.dto.request.RestaurantRequest;
import com.course.plazoleta.application.dto.response.RestaurantCompleteResponse;
import com.course.plazoleta.application.dto.response.RestaurantResponse;
import com.course.plazoleta.application.handler.IRestaurantHandler;
import com.course.plazoleta.application.mapper.request.IRestaurantRequestMapper;
import com.course.plazoleta.application.mapper.response.IRestaurantResponseMapper;
import com.course.plazoleta.domain.api.IRestaurantServicePort;
import com.course.plazoleta.domain.model.PageModel;
import com.course.plazoleta.domain.model.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantHandler implements IRestaurantHandler {

    private final IRestaurantServicePort restaurantServicePort;
    private final IRestaurantRequestMapper restaurantRequestMapper;
    private final IRestaurantResponseMapper restaurantResponseMapper;

    @Override
    public void saveRestaurant(RestaurantRequest restaurantRequest) {

        Restaurant restaurant = restaurantRequestMapper.toRestaurant(restaurantRequest);
        restaurantServicePort.saveRestaurant(restaurant);
    }

    @Override
    public RestaurantCompleteResponse getRestaurantById(long id) {
        Restaurant restaurant = restaurantServicePort.getRestaurantById(id);
        return restaurantResponseMapper.toCompleteResponse(restaurant);

    }

    @Override
    public PageModel<RestaurantResponse> getAllRestaurants(Integer page , Integer pageSize, String fieldToSort) {
        return restaurantResponseMapper.toResponseList(restaurantServicePort.getAllRestaurants(page,pageSize,fieldToSort));
    }

    @Override
    public void deleteRestaurantById(long id) {
        restaurantServicePort.deleteRestaurantById(id);
    }
}
