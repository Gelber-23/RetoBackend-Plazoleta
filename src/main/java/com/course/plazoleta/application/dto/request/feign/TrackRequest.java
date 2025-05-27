package com.course.plazoleta.application.dto.request.feign;

import com.course.plazoleta.domain.utils.constants.DtoConstants;
import com.course.plazoleta.domain.utils.constants.ValuesConstants;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrackRequest {
    @NotNull(message = DtoConstants.ID_ORDER_REQUIRED_MESSAGE)
    @Min(value = ValuesConstants.MIN_VALUE_FOR_NUMBERS, message = DtoConstants.ID_ORDER_REQUIRED_MESSAGE)
    private Long idOrder;
    @NotNull(message = DtoConstants.ID_CLIENT_REQUIRED_MESSAGE)
    @Min(value = ValuesConstants.MIN_VALUE_FOR_NUMBERS, message = DtoConstants.ID_CLIENT_REQUIRED_MESSAGE)
    private Long idClient;
    @NotNull(message = DtoConstants.ID_EMPLOYEE_REQUIRED_MESSAGE)
    @Min(value = ValuesConstants.MIN_VALUE_FOR_NUMBERS, message = DtoConstants.ID_EMPLOYEE_REQUIRED_MESSAGE)
    private Long idEmployee;
    @NotNull(message = DtoConstants.ID_EMPLOYEE_REQUIRED_MESSAGE)
    @Min(value = ValuesConstants.MIN_VALUE_FOR_NUMBERS, message = DtoConstants.ID_EMPLOYEE_REQUIRED_MESSAGE)
    private Long idRestaurant;
    @NotBlank(message = DtoConstants.PREVIOUS_STATE_REQUIRED_MESSAGE)
    private String previousState;
    @NotBlank(message = DtoConstants.NEW_STATE_REQUIRED_MESSAGE)
    private String newState;
}
