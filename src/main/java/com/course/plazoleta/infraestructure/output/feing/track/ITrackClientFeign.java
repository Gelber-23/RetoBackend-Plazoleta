package com.course.plazoleta.infraestructure.output.feing.track;

import com.course.plazoleta.application.dto.request.feign.MessageSmsRequest;
import com.course.plazoleta.application.dto.request.feign.TrackRequest;
import com.course.plazoleta.infraestructure.output.feing.ClientFeignConfig;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "traceability",
        path = "/track/",
        configuration = ClientFeignConfig.class
)
public interface ITrackClientFeign {
    @PostMapping(("/"))
    void createTrack(@Valid @RequestBody TrackRequest trackRequest);
}
