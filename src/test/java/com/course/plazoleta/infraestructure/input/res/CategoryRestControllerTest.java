package com.course.plazoleta.infraestructure.input.res;

import com.course.plazoleta.application.dto.request.CategoryRequest;
import com.course.plazoleta.application.dto.request.DishRequest;
import com.course.plazoleta.application.dto.response.CategoryResponse;
import com.course.plazoleta.application.dto.response.DishResponse;
import com.course.plazoleta.application.handler.ICategoryHandler;
import com.course.plazoleta.application.mapper.response.ICategoryResponseMapper;
import com.course.plazoleta.domain.model.Category;
import com.course.plazoleta.domain.model.Dish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryRestControllerTest {
    @Mock
    private ICategoryHandler categoryHandler;
    @Mock
    private ICategoryResponseMapper categoryResponseMapper;

    @InjectMocks
    private CategoryRestController categoryRestController;


    private Category category;
    @BeforeEach
    void setUp() {

        category = new Category();
        category.setId(1L);
        category.setName("Category");
        category.setDescription("Category 2");

    }

    @Test
    void shouldSaveCategorySuccessfully() {
        CategoryRequest request = new CategoryRequest();

        request.setName("Category");
        request.setDescription("Category 2");

        ResponseEntity<Void> response = categoryRestController.saveCategory(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(categoryHandler).saveCategory(request);
    }

    @Test
    void shouldGetCategoryByIdSuccessfully() {

        CategoryResponse categoryResponse =  categoryResponseMapper.toResponse(category);

        when(categoryHandler.getCategoryById(category.getId())).thenReturn(categoryResponse);

        ResponseEntity<CategoryResponse> response = categoryRestController.getCategoryById(category.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(categoryResponse, response.getBody());
        verify(categoryHandler).getCategoryById(category.getId());
    }

    @Test
    void shouldGetAllCategoriesSuccessfully() {
        List<CategoryResponse> responseList = Collections.singletonList(categoryResponseMapper.toResponse(category));

        when(categoryHandler.getAllCategories()).thenReturn(responseList);

        ResponseEntity<List<CategoryResponse>> response = categoryRestController.getAllCategories();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
        verify(categoryHandler).getAllCategories();
    }
}