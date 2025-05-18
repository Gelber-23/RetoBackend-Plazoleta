package com.course.plazoleta.infraestructure.output.jpa.mapper;

import com.course.plazoleta.domain.model.Dish;
import com.course.plazoleta.infraestructure.output.jpa.entity.DishEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE )
public interface IDishEntityMapper {
    @Mapping(target = "idCategory.id", source = "idCategory")
    DishEntity toEntity(Dish dish);
    @Mapping(target = "idCategory", source = "idCategory.id")
    Dish toModel(DishEntity dishEntity);

    List<Dish> toModelList(List<DishEntity> dishEntities);
}
