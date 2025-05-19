package com.course.plazoleta.application.dto.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryResponseTest {
    @Test
    void testNoArgsConstructorAndSettersGetters() {
        CategoryResponse response = new CategoryResponse();

        response.setId(10L);
        response.setName("Beverages");
        response.setDescription("Drinks and refreshments");

        assertEquals(10L, response.getId());
        assertEquals("Beverages", response.getName());
        assertEquals("Drinks and refreshments", response.getDescription());
    }

    @Test
    void testNullValues() {
        CategoryResponse response = new CategoryResponse();

        response.setId(null);
        response.setName(null);
        response.setDescription(null);

        assertNull(response.getId());
        assertNull(response.getName());
        assertNull(response.getDescription());
    }
}