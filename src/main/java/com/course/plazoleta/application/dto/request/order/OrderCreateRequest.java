package com.course.plazoleta.application.dto.request.order;

import com.course.plazoleta.domain.utils.constants.DtoConstants;
import com.course.plazoleta.domain.utils.constants.ValuesConstants;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class OrderCreateRequest {

    @NotNull(message = DtoConstants.FIELD_REQUIRED)
    @Size(min = ValuesConstants.MIN_VALUE_FOR_NUMBERS, message = DtoConstants.MUST_BE_ONE_DISH)

    private List<OrderDishCreateRequest> dishes;

    @NotNull(message = DtoConstants.FIELD_REQUIRED)
    @Min(value = ValuesConstants.MIN_VALUE_FOR_NUMBERS, message = DtoConstants.FIELD_NOT_NEGATIVE_MESSAGE)
    private Long  idRestaurant;
}
