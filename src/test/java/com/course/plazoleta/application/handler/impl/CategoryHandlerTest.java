package com.course.plazoleta.application.handler.impl;

import com.course.plazoleta.application.dto.request.CategoryRequest;
import com.course.plazoleta.application.dto.response.CategoryResponse;
import com.course.plazoleta.application.mapper.request.ICategoryRequestMapper;
import com.course.plazoleta.application.mapper.response.ICategoryResponseMapper;
import com.course.plazoleta.domain.api.ICategoryServicePort;
import com.course.plazoleta.domain.model.Category;
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
class CategoryHandlerTest {

    @Mock
    private ICategoryServicePort categoryServicePort;

    @Mock
    private ICategoryRequestMapper categoryRequestMapper;

    @Mock
    private ICategoryResponseMapper categoryResponseMapper;

    @InjectMocks
    private CategoryHandler categoryHandler;

    @Test
    void saveCategory_ShouldMapAndCallService() {
        CategoryRequest request = new CategoryRequest();
        Category category = new Category();

        when(categoryRequestMapper.toCategory(request)).thenReturn(category);

        categoryHandler.saveCategory(request);

        verify(categoryRequestMapper).toCategory(request);
        verify(categoryServicePort).saveCategory(category);
    }

    @Test
    void getCategoryById_ShouldReturnMappedResponse() {
        long id = 1L;
        Category category = new Category();
        CategoryResponse response = new CategoryResponse();

        when(categoryServicePort.getCategoryById(id)).thenReturn(category);
        when(categoryResponseMapper.toResponse(category)).thenReturn(response);

        CategoryResponse result = categoryHandler.getCategoryById(id);

        assertEquals(response, result);
        verify(categoryServicePort).getCategoryById(id);
        verify(categoryResponseMapper).toResponse(category);
    }

    @Test
    void getAllCategories_ShouldReturnMappedList() {
        List<Category> categories = Collections.singletonList(new Category());
        List<CategoryResponse> responses = Collections.singletonList(new CategoryResponse());

        when(categoryServicePort.getAllCategories()).thenReturn(categories);
        when(categoryResponseMapper.toResponseList(categories)).thenReturn(responses);

        List<CategoryResponse> result = categoryHandler.getAllCategories();

        assertEquals(responses, result);
        verify(categoryServicePort).getAllCategories();
        verify(categoryResponseMapper).toResponseList(categories);
    }

}