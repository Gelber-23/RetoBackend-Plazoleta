package com.course.plazoleta.application.dto.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DishListResponseTest {

    @Test
    void testGettersAndSetters() {
        DishListResponse dish = new DishListResponse();

        dish.setName("Pizza");
        dish.setPrice(1500);
        dish.setUrlImage("http://image.com/pizza.jpg");

        assertEquals("Pizza", dish.getName());
        assertEquals(1500, dish.getPrice());
        assertEquals("http://image.com/pizza.jpg", dish.getUrlImage());
    }

}