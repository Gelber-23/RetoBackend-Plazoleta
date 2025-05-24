package com.course.plazoleta.application.mapper.response;

import com.course.plazoleta.application.dto.response.DishListResponse;
import com.course.plazoleta.application.dto.response.DishResponse;
import com.course.plazoleta.domain.model.Dish;
import com.course.plazoleta.domain.model.PageModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IDishResponseMapper {
    PageModel<DishListResponse> toResponsePageModelList(PageModel<Dish> dishPageModel);
    DishListResponse toResponseDishItemList(Dish dish);
    DishResponse toResponse(Dish dish);
    List<DishResponse> toResponseList(List<Dish> dishList);
}
