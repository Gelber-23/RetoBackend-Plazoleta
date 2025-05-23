package com.course.plazoleta.application.dto.request;

import com.course.plazoleta.domain.utils.constants.DtoConstants;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequest {
    @NotBlank(message = DtoConstants.FIELD_REQUIRED)
    private String name;

    @NotBlank(message = DtoConstants.FIELD_REQUIRED)
    private String description;

}
