package com.course.plazoleta.application.handler.impl;

import com.course.plazoleta.application.dto.request.CategoryRequest;
import com.course.plazoleta.application.dto.response.CategoryResponse;
import com.course.plazoleta.application.handler.ICategoryHandler;
import com.course.plazoleta.application.mapper.request.ICategoryRequestMapper;
import com.course.plazoleta.application.mapper.response.ICategoryResponseMapper;
import com.course.plazoleta.domain.api.ICategoryServicePort;
import com.course.plazoleta.domain.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryHandler implements ICategoryHandler {
    private final ICategoryServicePort categoryServicePort;
    private final ICategoryRequestMapper categoryRequestMapper;
    private final ICategoryResponseMapper categoryResponseMapper;

    @Override
    public void saveCategory(CategoryRequest categoryRequest) {
        Category category = categoryRequestMapper.toCategory(categoryRequest);
        categoryServicePort.saveCategory(category);
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryServicePort.getCategoryById(id);
        return categoryResponseMapper.toResponse(category);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryResponseMapper.toResponseList(categoryServicePort.getAllCategories());
    }
}
