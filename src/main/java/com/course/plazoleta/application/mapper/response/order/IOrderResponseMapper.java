package com.course.plazoleta.application.mapper.response.order;

import com.course.plazoleta.application.dto.response.order.OrderResponse;
import com.course.plazoleta.domain.model.Order;
import com.course.plazoleta.domain.model.PageModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderResponseMapper {
    OrderResponse toResponse(Order order);
    PageModel<OrderResponse> toResponsePageList(PageModel<Order> orderPageModel);
}
