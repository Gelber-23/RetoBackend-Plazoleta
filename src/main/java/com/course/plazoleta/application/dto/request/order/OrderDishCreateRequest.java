package com.course.plazoleta.application.dto.request.order;

import com.course.plazoleta.domain.utils.constants.DtoConstants;
import com.course.plazoleta.domain.utils.constants.ValuesConstants;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDishCreateRequest {

    @NotNull(message = DtoConstants.FIELD_REQUIRED)
    @Min(value = ValuesConstants.MIN_VALUE_FOR_NUMBERS, message = DtoConstants.FIELD_NOT_NEGATIVE_MESSAGE)
    private Long idDish;
    @NotNull(message = DtoConstants.FIELD_REQUIRED)
    @Min(value = ValuesConstants.MIN_VALUE_FOR_NUMBERS, message = DtoConstants.FIELD_NOT_NEGATIVE_MESSAGE)
    private Integer quantity;
}
