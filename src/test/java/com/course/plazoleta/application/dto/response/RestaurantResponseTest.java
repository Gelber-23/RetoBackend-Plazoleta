package com.course.plazoleta.application.dto.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantResponseTest {

    @Test
    void shouldSetAndGetFieldsCorrectly() {
        RestaurantResponse response = new RestaurantResponse();
        response.setName("Restaurant");
        response.setUrlLogo("https://example.com/logo.jpg");

        assertEquals("Restaurant", response.getName());
        assertEquals("https://example.com/logo.jpg", response.getUrlLogo());
    }

    @Test
    void shouldAllowNullValues() {
        RestaurantResponse response = new RestaurantResponse();
        response.setName(null);
        response.setUrlLogo(null);

        assertNull(response.getName());
        assertNull(response.getUrlLogo());
    }

    @Test
    void shouldCreateInstanceWithoutArgs() {
        RestaurantResponse response = new RestaurantResponse();
        assertNotNull(response);
    }

}