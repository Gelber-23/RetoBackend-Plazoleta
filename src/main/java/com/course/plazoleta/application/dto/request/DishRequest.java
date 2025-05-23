package com.course.plazoleta.application.dto.request;


import com.course.plazoleta.domain.utils.constants.DtoConstants;
import com.course.plazoleta.domain.utils.constants.ValuesConstants;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
public class DishRequest {


    @NotBlank(message = DtoConstants.FIELD_REQUIRED)
    private String name;

    @NotNull(message = DtoConstants.FIELD_REQUIRED)
    @Min(value = ValuesConstants.MIN_VALUE_FOR_NUMBERS, message = DtoConstants.FIELD_NOT_NEGATIVE_MESSAGE)
    private Integer  price;

    @NotBlank(message = DtoConstants.FIELD_REQUIRED)
    private String description;

    @NotBlank(message = DtoConstants.FIELD_REQUIRED)
    @URL(message =DtoConstants.FIELD_URL_INVALID_FORMAT_MESSAGE)
    private String urlImage;


    @NotNull(message = DtoConstants.FIELD_REQUIRED)
    @Min(value = ValuesConstants.MIN_VALUE_FOR_NUMBERS, message = DtoConstants.FIELD_NOT_NEGATIVE_MESSAGE)
    private long idCategory;

    @NotNull(message = DtoConstants.FIELD_REQUIRED)
    @Min(value = ValuesConstants.MIN_VALUE_FOR_NUMBERS, message = DtoConstants.FIELD_NOT_NEGATIVE_MESSAGE)
    private long idRestaurant;
}
