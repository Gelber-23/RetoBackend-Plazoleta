package com.course.plazoleta.application.mapper.response;

import com.course.plazoleta.application.dto.response.RestaurantResponse;
import com.course.plazoleta.domain.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRestaurantResponseMapper {
    RestaurantResponse toResponse(Restaurant restaurant);
    List<RestaurantResponse> toResponseList(List<Restaurant> restaurantList);
}
