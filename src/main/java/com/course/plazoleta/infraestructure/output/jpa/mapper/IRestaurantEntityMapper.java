package com.course.plazoleta.infraestructure.output.jpa.mapper;

import com.course.plazoleta.domain.model.Restaurant;
import com.course.plazoleta.infraestructure.output.jpa.entity.RestaurantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE )
public interface IRestaurantEntityMapper {


    RestaurantEntity toEntity(Restaurant role);

    Restaurant toModel(RestaurantEntity restaurantEntity);

    List<Restaurant> toModelList(List<RestaurantEntity> restaurantEntityList);
}
