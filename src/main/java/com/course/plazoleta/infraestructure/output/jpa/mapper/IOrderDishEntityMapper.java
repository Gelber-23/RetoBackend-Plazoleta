package com.course.plazoleta.infraestructure.output.jpa.mapper;

import com.course.plazoleta.domain.model.OrderDish;
import com.course.plazoleta.infraestructure.output.jpa.entity.OrderDishEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE )
public interface IOrderDishEntityMapper {

    @Mapping(target = "idOrder.id", source = "idOrder")
    @Mapping(target = "idDish.id", source = "idDish")
    OrderDishEntity toEntity(OrderDish orderDish);

    @Mapping(target = "idOrder", source = "idOrder.id")
    @Mapping(target = "idDish", source = "idDish.id")
    OrderDish toOrderModel(OrderDishEntity orderDishEntity);
}
