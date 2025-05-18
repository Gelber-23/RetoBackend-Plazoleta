package com.course.plazoleta.application.mapper.request;


import com.course.plazoleta.application.dto.request.DishRequest;
import com.course.plazoleta.domain.model.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IDishRequestMapper {


    Dish toDish (DishRequest dishRequest);
}
