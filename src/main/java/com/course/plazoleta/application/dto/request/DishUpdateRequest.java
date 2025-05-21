package com.course.plazoleta.application.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishUpdateRequest {

    @NotNull(message = "Id is required")
    @Min(value = 1, message = "The ID cannot be negative or 0")
    private long  id;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Price is required")
    @Min(value = 1, message = "The price cannot be negative or 0")
    private Integer  price;
}
