package com.course.plazoleta.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    private Category category;
    @BeforeEach
    void setUp() {
        category = new Category();
    }

    @Test
    void testNoArgsConstructorAndSettersGetters() {

        category.setId(1L);
        category.setName("Fast Food");
        category.setDescription("Quick meals");

        assertEquals(1L, category.getId());
        assertEquals("Fast Food", category.getName());
        assertEquals("Quick meals", category.getDescription());
    }

    @Test
    void testAllArgsConstructor() {
        category = new Category(2L, "Desserts", "Sweet dishes");

        assertEquals(2L, category.getId());
        assertEquals("Desserts", category.getName());
        assertEquals("Sweet dishes", category.getDescription());
    }

}