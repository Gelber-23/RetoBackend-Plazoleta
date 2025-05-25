package com.course.plazoleta.infraestructure.output.jpa.mapper;

import com.course.plazoleta.domain.model.Order;
import com.course.plazoleta.infraestructure.output.jpa.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE )
public interface IOrderEntityMapper {

    @Mapping(target = "idRestaurant.id", source = "idRestaurant")
    OrderEntity toEntity(Order order);

    @Mapping(target = "idRestaurant", source = "idRestaurant.id")
    Order toOrderModel(OrderEntity orderEntity);
}
