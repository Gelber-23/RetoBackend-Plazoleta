package com.course.plazoleta.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {

    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant();
    }

    @Test
    void testNoArgsConstructorAndSetters() {
        restaurant.setId(1L);
        restaurant.setName("Restaurant");
        restaurant.setAddress("Address");
        restaurant.setId_owner(100L);
        restaurant.setPhone("+1234567890");
        restaurant.setUrlLogo("https://logo.com/logo.png");
        restaurant.setNit("123456789");

        assertEquals(1L, restaurant.getId());
        assertEquals("Restaurant", restaurant.getName());
        assertEquals("Address", restaurant.getAddress());
        assertEquals(100L, restaurant.getId_owner());
        assertEquals("+1234567890", restaurant.getPhone());
        assertEquals("https://logo.com/logo.png", restaurant.getUrlLogo());
        assertEquals("123456789", restaurant.getNit());
    }

    @Test
    void testAllArgsConstructor() {
        Restaurant rest = new Restaurant(1L, "Restaurant", "Address", 100L, "+1234567890", "https://logo.com/logo.png", "123456789");

        assertEquals(1L, rest.getId());
        assertEquals("Restaurant", rest.getName());
        assertEquals("Address", rest.getAddress());
        assertEquals(100L, rest.getId_owner());
        assertEquals("+1234567890", rest.getPhone());
        assertEquals("https://logo.com/logo.png", rest.getUrlLogo());
        assertEquals("123456789", rest.getNit());
    }

    @Test
    void testSettersWithNullValues() {
        restaurant.setName(null);
        restaurant.setAddress(null);
        restaurant.setPhone(null);
        restaurant.setUrlLogo(null);
        restaurant.setNit(null);

        assertNull(restaurant.getName());
        assertNull(restaurant.getAddress());
        assertNull(restaurant.getPhone());
        assertNull(restaurant.getUrlLogo());
        assertNull(restaurant.getNit());
    }
}