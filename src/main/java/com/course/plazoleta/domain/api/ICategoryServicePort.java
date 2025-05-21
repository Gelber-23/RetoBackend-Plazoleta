package com.course.plazoleta.domain.api;



import com.course.plazoleta.domain.model.Category;

import java.util.List;

public interface ICategoryServicePort {
    void saveCategory(Category category);

    Category getCategoryById(long id);

    List<Category> getAllCategories();
}
