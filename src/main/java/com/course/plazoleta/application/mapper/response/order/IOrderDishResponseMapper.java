package com.course.plazoleta.application.mapper.response.order;

import com.course.plazoleta.application.dto.response.order.OrderDishResponse;
import com.course.plazoleta.domain.model.OrderDish;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderDishResponseMapper {
    OrderDishResponse toResponse(OrderDish orderDish);
}
