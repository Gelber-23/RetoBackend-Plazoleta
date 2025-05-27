package com.course.plazoleta.application.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantCompleteResponse {
    private Long id;
    private String name;
    private String address;
    private Long id_owner;
    private String phone;
    private String urlLogo;
    private String  nit;
}
