package com.course.plazoleta.application.dto.request;

import com.course.plazoleta.domain.utils.constants.DtoConstants;
import com.course.plazoleta.domain.utils.constants.ValuesConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
public class RestaurantRequest {

    @NotBlank(message = DtoConstants.FIELD_REQUIRED)
    @Pattern(regexp = DtoConstants.NOT_ONLY_NUMBERS_REGEX, message = DtoConstants.FIELD_NOT_ONLY_NUMBER_MESSAGE)
    @Schema(description =DtoConstants.DESCRIPTION_SCHEMA_DESCRIPTION, example = DtoConstants.DESCRIPTION_SCHEMA_EXAMPLE)

    private String name;

    @NotBlank(message = DtoConstants.FIELD_REQUIRED)
    private String address;

    @NotNull(message = DtoConstants.FIELD_REQUIRED)
    @Min(value = ValuesConstants.MIN_VALUE_FOR_NUMBERS , message = DtoConstants.FIELD_NOT_NEGATIVE_MESSAGE)
    private long id_owner;

    @NotBlank(message =DtoConstants.FIELD_REQUIRED)
    @Size(max = ValuesConstants.MAX_LENGTH_PHONE, message = DtoConstants.FIELD_MUST_HAVE_13_CHARACTERS)
    @Pattern(regexp =DtoConstants.PHONE_REGEX, message = DtoConstants.FIELD_PHONE_FORMAT_MESSAGE)
    private String phone;

    @NotBlank(message = DtoConstants.FIELD_REQUIRED)
    @URL(message = DtoConstants.FIELD_URL_INVALID_FORMAT_MESSAGE)
    private String urlLogo;

    @NotBlank(message =  DtoConstants.FIELD_REQUIRED)
    @Pattern(regexp = DtoConstants.ONLY_NUMBERS_REGEX, message = DtoConstants.FIELD_ONLY_NUMBER_REQUIRED)
    @Schema(description = DtoConstants.NIT_SCHEMA_DESCRIPTION, example = DtoConstants.NIT_SCHEMA_EXAMPLE)
    private String nit;

}
