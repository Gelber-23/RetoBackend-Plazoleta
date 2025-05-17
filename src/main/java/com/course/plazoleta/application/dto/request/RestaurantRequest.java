package com.course.plazoleta.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
public class RestaurantRequest {

    @NotBlank(message = "Name is required")
    @Pattern(regexp = ".*\\D.*", message = "The name cannot be composed only of numbers")
    private String name;

    @NotBlank(message = "Address is required")
    private String address;

    @NotNull(message = "ID Owner is required")
    @Min(value = 1, message = "The owner id cannot be negative or 0")
    private int id_owner;

    @NotBlank(message = "Phone is required")
    @Size(max = 13, message = "Phone number must be at most 13 characters long")
    @Pattern(regexp = "^\\+?\\d{1,12}$", message = "Invalid phone number format")
    private String phone;

    @NotBlank(message = "Phone is required")
    @URL(message = "Invalid URL format")
    private String urlLogo;

    @NotBlank(message = "NIT is required")
    @Pattern(regexp = "^\\d+$", message = "NIT must contain only numbers")
    private String nit;

}
