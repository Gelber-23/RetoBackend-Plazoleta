package com.course.plazoleta.infraestructure.output.jpa.adapter;

import com.course.plazoleta.domain.model.Category;
import com.course.plazoleta.domain.spi.ICategoryPersistencePort;
import com.course.plazoleta.infraestructure.exception.NoDataFoundException;
import com.course.plazoleta.infraestructure.output.jpa.entity.CategoryEntity;
import com.course.plazoleta.infraestructure.output.jpa.mapper.ICategoryEntityMapper;
import com.course.plazoleta.infraestructure.output.jpa.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CategoryJpaAdapter implements ICategoryPersistencePort {

    private final ICategoryRepository categoryRepository;
    private  final ICategoryEntityMapper categoryEntityMapper;

    @Override
    public void saveCategory(Category category) {
        categoryRepository.save(categoryEntityMapper.toEntity(category));
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryEntityMapper.toModel(categoryRepository.findById(id)
                .orElseThrow(NoDataFoundException::new));
    }

    @Override
    public List<Category> getAllCategories() {
        List<CategoryEntity> categoryEntities = categoryRepository.findAll();
        if (categoryEntities.isEmpty()) {
            throw new NoDataFoundException();
        }
        return categoryEntityMapper.toModelList(categoryEntities);
    }
}
