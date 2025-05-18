package com.course.plazoleta.application.handler;

import com.course.plazoleta.application.dto.request.CategoryRequest;
import com.course.plazoleta.application.dto.response.CategoryResponse;


import java.util.List;

public interface ICategoryHandler {

    void saveCategory(CategoryRequest categoryRequest);

    CategoryResponse getCategoryById(Long id);

    List<CategoryResponse> getAllCategories();
}
