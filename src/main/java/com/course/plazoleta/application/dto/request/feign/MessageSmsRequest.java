package com.course.plazoleta.application.dto.request.feign;


import com.course.plazoleta.domain.utils.constants.DtoConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageSmsRequest {

    @NotBlank(message = DtoConstants.FIELD_PHONE_REQUIRED)
    @Pattern(regexp = DtoConstants.PHONE_REGEX, message = DtoConstants.FIELD_PHONE_FORMAT_MESSAGE)
    private String phone;

    @NotBlank(message = DtoConstants.FIELD_MESSAGE_REQUIRED)
    private String message;
}
