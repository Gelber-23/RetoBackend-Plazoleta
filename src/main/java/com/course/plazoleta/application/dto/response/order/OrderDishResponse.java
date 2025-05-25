package com.course.plazoleta.application.dto.response.order;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrderDishResponse {
    private Long id ;
    private Long idOrder ;
    private Long idDish;
    private int quantity;
}
