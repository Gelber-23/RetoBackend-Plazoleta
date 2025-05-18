package com.course.plazoleta.application.mapper.request;

import com.course.plazoleta.application.dto.request.CategoryRequest;
import com.course.plazoleta.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ICategoryRequestMapper {



    Category toCategory (CategoryRequest categoryRequest);
}
