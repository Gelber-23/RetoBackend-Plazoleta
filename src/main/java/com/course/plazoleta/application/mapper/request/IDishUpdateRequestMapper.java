package com.course.plazoleta.application.mapper.request;


import com.course.plazoleta.application.dto.request.DishUpdateRequest;
import com.course.plazoleta.domain.model.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IDishUpdateRequestMapper {
    Dish toDish (DishUpdateRequest dishUpdateRequest);
}
