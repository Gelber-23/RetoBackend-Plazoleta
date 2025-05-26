package com.course.plazoleta.infraestructure.output.feing.twilio.mapper;

import com.course.plazoleta.application.dto.request.MessageSmsRequest;
import com.course.plazoleta.domain.model.MessageSms;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface ITwilioMapper {
    MessageSmsRequest toMessageRequest(MessageSms messageSms);
}
