package com.course.plazoleta.domain.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PageModelTest {
    @Test
    void testPageModelAllMethods() {
        List<String> content = List.of("item1", "item2");

        PageModel<String> pageModel = new PageModel<>(content, 1, 10, 50L, 5);

        assertEquals(content, pageModel.getContent());
        assertEquals(1, pageModel.getPage());
        assertEquals(10, pageModel.getSize());
        assertEquals(50L, pageModel.getTotalElements());
        assertEquals(5, pageModel.getTotalPages());
    }

    @Test
    void testPageModelConstructorWithNullContentThrowsException() {
        assertThrows(NullPointerException.class, () -> new PageModel<>(null, 1, 10, 50L, 5));
    }

}