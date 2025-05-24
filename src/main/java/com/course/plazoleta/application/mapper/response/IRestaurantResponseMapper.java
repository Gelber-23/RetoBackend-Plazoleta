package com.course.plazoleta.application.mapper.response;

import com.course.plazoleta.application.dto.response.RestaurantResponse;
import com.course.plazoleta.domain.model.PageModel;
import com.course.plazoleta.domain.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRestaurantResponseMapper {
    RestaurantResponse toResponse(Restaurant restaurant);
    PageModel<RestaurantResponse> toResponseList(PageModel<Restaurant> restaurantList);
}
