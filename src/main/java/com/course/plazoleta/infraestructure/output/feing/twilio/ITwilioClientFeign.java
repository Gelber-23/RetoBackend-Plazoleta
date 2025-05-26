package com.course.plazoleta.infraestructure.output.feing.twilio;

import com.course.plazoleta.application.dto.request.MessageSmsRequest;
import com.course.plazoleta.infraestructure.output.feing.ClientFeignConfig;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "messaging",
        path = "/message/",
        configuration = ClientFeignConfig.class
)
public interface ITwilioClientFeign {

    @PostMapping(("/"))
    void sendSmsMessage(@Valid @RequestBody MessageSmsRequest messageSmsRequest);
}
