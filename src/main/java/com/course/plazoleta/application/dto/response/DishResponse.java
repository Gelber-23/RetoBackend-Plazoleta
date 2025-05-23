package com.course.plazoleta.application.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishResponse {

    private long id;
    private String name;
    private long idCategory;
    private String description;
    private int price;
    private String urlImage;
    private long idRestaurant;
    private Boolean active;
}
