package com.course.plazoleta.infraestructure.input.res;

import com.course.plazoleta.application.dto.request.CategoryRequest;
import com.course.plazoleta.application.dto.response.CategoryResponse;
import com.course.plazoleta.application.handler.ICategoryHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class CategoryRestControllerTest {
    @InjectMocks
    private CategoryRestController categoryRestController;

    @Mock
    private ICategoryHandler categoryHandler;

    @Test
    void saveCategory_shouldReturnCreated() {
        CategoryRequest request = new CategoryRequest();
        ResponseEntity<Void> response = categoryRestController.saveCategory(request);
        verify(categoryHandler).saveCategory(request);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void getCategoryById_shouldReturnCategory() {
        long id = 1L;
        CategoryResponse expected = new CategoryResponse();
        when(categoryHandler.getCategoryById(id)).thenReturn(expected);
        ResponseEntity<CategoryResponse> response = categoryRestController.getCategoryById(id);
        verify(categoryHandler).getCategoryById(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
    }

    @Test
    void getAllCategories_shouldReturnList() {
        List<CategoryResponse> expectedList = Collections.singletonList(new CategoryResponse());
        when(categoryHandler.getAllCategories()).thenReturn(expectedList);
        ResponseEntity<List<CategoryResponse>> response = categoryRestController.getAllCategories();
        verify(categoryHandler).getAllCategories();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedList, response.getBody());
    }

}