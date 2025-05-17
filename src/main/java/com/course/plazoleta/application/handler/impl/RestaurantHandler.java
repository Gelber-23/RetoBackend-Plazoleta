package com.course.plazoleta.application.handler.impl;

import com.course.plazoleta.application.dto.request.RestaurantRequest;
import com.course.plazoleta.application.dto.response.RestaurantResponse;
import com.course.plazoleta.application.handler.IRestaurantHandler;
import com.course.plazoleta.application.mapper.request.IRestaurantRequestMapper;
import com.course.plazoleta.application.mapper.response.IRestaurantResponseMapper;
import com.course.plazoleta.domain.api.IRestaurantServicePort;
import com.course.plazoleta.domain.model.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public RestaurantResponse getRestaurantById(int id) {
        Restaurant restaurant = restaurantServicePort.getRestaurantById(id);
        return restaurantResponseMapper.toResponse(restaurant);

    }

    @Override
    public List<RestaurantResponse> getAllRestaurants() {
        return restaurantResponseMapper.toResponseList(restaurantServicePort.getAllRestaurants());
    }

    @Override
    public void deleteRestaurantById(int id) {
        restaurantServicePort.deleteRestaurantById(id);
    }
}
