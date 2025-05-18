package com.course.plazoleta.application.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishResponse {

    private Long id;
    private String name;
    private int idCategory;
    private String description;
    private int price;
    private String urlImage;
    private int idRestaurant;
    private Boolean active;
}
