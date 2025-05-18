package com.course.plazoleta.application.dto.request;


import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
public class DishRequest {


    @NotBlank(message = "Name is required")
    private String name;


    @NotNull(message = "Price is required")
    @Min(value = 1, message = "The price cannot be negative or 0")
    private Integer  price;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Image is required")
    @URL(message = "Invalid URL format")
    private String urlImage;


    @NotNull(message = "Category is required")
    @Min(value = 1, message = "The category cannot be negative or 0")
    private Integer idCategory;
}
