package com.course.plazoleta.application.mapper.request;

import com.course.plazoleta.application.dto.request.RestaurantRequest;
import com.course.plazoleta.domain.model.Restaurant;
import org.mapstruct.Mapper;

import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRestaurantRequestMapper {

    Restaurant toRestaurant(RestaurantRequest restaurantRequest);
}
