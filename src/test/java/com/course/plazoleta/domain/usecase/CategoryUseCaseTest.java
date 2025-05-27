package com.course.plazoleta.domain.usecase;

import com.course.plazoleta.domain.model.Category;
import com.course.plazoleta.domain.spi.ICategoryPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryUseCaseTest {

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private CategoryUseCase categoryUseCase;

    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category(2L, "Desserts", "Sweet dishes");
    }

    @Test
    void shouldSaveCategory() {
        categoryUseCase.saveCategory(category);
        verify(categoryPersistencePort).saveCategory(category);
    }

    @Test
    void shouldReturnCategoryById() {
        when(categoryPersistencePort.getCategoryById(1L)).thenReturn(category);

        Category result = categoryPersistencePort.getCategoryById(1L);

        assertNotNull(result);
        assertEquals(category.getId(), result.getId());
        verify(categoryPersistencePort).getCategoryById(1L);
    }

    @Test
    void shouldReturnAllCategories() {
        List<Category> categoryList = Collections.singletonList(category);

        when(categoryPersistencePort.getAllCategories()).thenReturn(categoryList);

        List<Category> result = categoryUseCase.getAllCategories();

        assertEquals(1, result.size());
        assertEquals("Desserts", result.getFirst().getName());
        verify(categoryPersistencePort).getAllCategories();
    }

    @Test
    void getCategoryById_shouldReturnCategory() {
        long categoryId = 1L;
        Category expectedCategory = new Category();
        expectedCategory.setId(categoryId);
        expectedCategory.setName("Main");

        when(categoryPersistencePort.getCategoryById(categoryId)).thenReturn(expectedCategory);

        Category result = categoryUseCase.getCategoryById(categoryId);

        assertNotNull(result);
        assertEquals(categoryId, result.getId());
        assertEquals("Main", result.getName());
        verify(categoryPersistencePort).getCategoryById(categoryId);
    }
}