package com.course.plazoleta.application.dto.response.order;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class OrderResponse {
    private Long id ;
    private Long idClient;
    private Long idRestaurant;
    private String state ;
    private Date date;
    private Long idChef ;
    private List<OrderDishResponse> dishes ;
    private String pin ;
}
