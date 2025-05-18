package com.course.plazoleta.infraestructure.output.jpa.mapper;

import com.course.plazoleta.domain.model.Category;
import com.course.plazoleta.infraestructure.output.jpa.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE )
public interface ICategoryEntityMapper {
    CategoryEntity toEntity(Category category);

    Category toModel(CategoryEntity categoryEntity);

    List<Category> toModelList(List<CategoryEntity> categoryEntities);
}
