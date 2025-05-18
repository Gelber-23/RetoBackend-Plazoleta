package com.course.plazoleta.application.dto.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DishResponseTest {
    @Test
    void testGettersAndSetters() {
        DishResponse response = new DishResponse();

        response.setId(1L);
        response.setName("Pizza");
        response.setIdCategory(2);
        response.setDescription("Cheese pizza");
        response.setPrice(1500);
        response.setUrlImage("http://example.com/pizza.png");
        response.setIdRestaurant(5);
        response.setActive(true);

        assertEquals(1L, response.getId());
        assertEquals("Pizza", response.getName());
        assertEquals(2, response.getIdCategory());
        assertEquals("Cheese pizza", response.getDescription());
        assertEquals(1500, response.getPrice());
        assertEquals("http://example.com/pizza.png", response.getUrlImage());
        assertEquals(5, response.getIdRestaurant());
        assertTrue(response.getActive());
    }

    @Test
    void testNullValues() {
        DishResponse response = new DishResponse();

        response.setId(null);
        response.setName(null);
        response.setDescription(null);
        response.setUrlImage(null);
        response.setActive(null);

        assertNull(response.getId());
        assertNull(response.getName());
        assertNull(response.getDescription());
        assertNull(response.getUrlImage());
        assertNull(response.getActive());
    }

}