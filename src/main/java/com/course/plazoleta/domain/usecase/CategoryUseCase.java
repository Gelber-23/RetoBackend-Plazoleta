package com.course.plazoleta.domain.usecase;

import com.course.plazoleta.domain.api.ICategoryServicePort;
import com.course.plazoleta.domain.model.Category;
import com.course.plazoleta.domain.spi.ICategoryPersistencePort;

import java.util.List;

public class CategoryUseCase implements ICategoryServicePort {

    private final ICategoryPersistencePort categoryPersistencePort;

    public CategoryUseCase(ICategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public void saveCategory(Category category) {
        categoryPersistencePort.saveCategory(category);
    }

    @Override
    public Category getCategoryById(long id) {
        return categoryPersistencePort.getCategoryById(id);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryPersistencePort.getAllCategories();
    }
}
