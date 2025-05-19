package com.course.plazoleta.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DishTest {

    private Dish dish;
    @BeforeEach
    void setUp() {
        dish = new Dish();
    }

    @Test
    void testNoArgsConstructorAndSetters() {
        dish.setId(1L);
        dish.setName("Dish");
        dish.setDescription("Dish New");
        dish.setIdCategory(1L);
        dish.setActive(true);
        dish.setPrice(10);
        dish.setIdRestaurant(1);
        dish.setUrlImage("https://logo.com/logo.png");

        assertEquals(1, dish.getId());
        assertEquals("Dish", dish.getName());
        assertEquals("Dish New", dish.getDescription());
        assertEquals(1, dish.getIdCategory());
        assertEquals(true, dish.getActive());
        assertEquals("https://logo.com/logo.png", dish.getUrlImage());
        assertEquals(10, dish.getPrice());
        assertEquals(1, dish.getIdRestaurant());
    }

    @Test
    void testAllArgsConstructor() {
        dish = new Dish(1L, "Burger", 3L, "Beef Burger", 2000, "http://image.com/burger.png", 7, false);

        assertEquals(1L, dish.getId());
        assertEquals("Burger", dish.getName());
        assertEquals(3, dish.getIdCategory());
        assertEquals("Beef Burger", dish.getDescription());
        assertEquals(2000, dish.getPrice());
        assertEquals("http://image.com/burger.png", dish.getUrlImage());
        assertEquals(7, dish.getIdRestaurant());
        assertFalse(dish.getActive());
    }
}