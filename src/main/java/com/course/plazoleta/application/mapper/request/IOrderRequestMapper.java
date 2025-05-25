package com.course.plazoleta.application.mapper.request;

import com.course.plazoleta.application.dto.request.order.OrderCreateRequest;
import com.course.plazoleta.domain.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderRequestMapper {
    Order toOrder(OrderCreateRequest orderCreateRequest);
}
