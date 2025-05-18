package com.course.plazoleta.domain.spi;

import com.course.plazoleta.domain.model.Category;

import java.util.List;

public interface ICategoryPersistencePort {
    void saveCategory(Category category);

    Category getCategoryById(Long id);

    List<Category> getAllCategories();
}
