package com.course.plazoleta.infraestructure.output.jpa.adapter;

import com.course.plazoleta.domain.exception.NoDataFoundException;
import com.course.plazoleta.domain.model.Category;
import com.course.plazoleta.infraestructure.output.jpa.entity.CategoryEntity;
import com.course.plazoleta.infraestructure.output.jpa.mapper.ICategoryEntityMapper;
import com.course.plazoleta.infraestructure.output.jpa.repository.ICategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryJpaAdapterTest {


    @Mock
    private ICategoryRepository categoryRepository;

    @Mock
    private ICategoryEntityMapper categoryEntityMapper;

    @InjectMocks
    private CategoryJpaAdapter categoryJpaAdapter;
    @Test
    void saveCategory_shouldCallRepositorySaveWithMappedEntity() {
        Category category = new Category();
        CategoryEntity entity = new CategoryEntity();
        when(categoryEntityMapper.toEntity(category)).thenReturn(entity);

        categoryJpaAdapter.saveCategory(category);

        verify(categoryRepository).save(entity);
    }

    @Test
    void getCategoryById_shouldReturnMappedModel() {
        long id = 1L;
        CategoryEntity entity = new CategoryEntity();
        Category model = new Category();

        when(categoryRepository.findById(id)).thenReturn(Optional.of(entity));
        when(categoryEntityMapper.toModel(entity)).thenReturn(model);

        Category result = categoryJpaAdapter.getCategoryById(id);

        verify(categoryRepository).findById(id);
        verify(categoryEntityMapper).toModel(entity);
        assertEquals(model, result);
    }

    @Test
    void getCategoryById_shouldThrowExceptionWhenNotFound() {
        long id = 1L;
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoDataFoundException.class, () -> categoryJpaAdapter.getCategoryById(id));
    }

    @Test
    void getAllCategories_shouldReturnMappedList() {
        CategoryEntity entity = new CategoryEntity();
        Category model = new Category();
        List<CategoryEntity> entityList = Collections.singletonList(entity);
        List<Category> modelList = Collections.singletonList(model);

        when(categoryRepository.findAll()).thenReturn(entityList);
        when(categoryEntityMapper.toModelList(entityList)).thenReturn(modelList);

        List<Category> result = categoryJpaAdapter.getAllCategories();

        verify(categoryRepository).findAll();
        verify(categoryEntityMapper).toModelList(entityList);
        assertEquals(modelList, result);
    }

    @Test
    void getAllCategories_shouldThrowExceptionWhenEmpty() {
        when(categoryRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(NoDataFoundException.class, () -> categoryJpaAdapter.getAllCategories());
    }

}