package com.course.plazoleta.infraestructure.output.feing.user;

import com.course.plazoleta.domain.model.User;
import com.course.plazoleta.infraestructure.output.feing.ClientFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "users",
        path = "/users/",
        configuration = ClientFeignConfig.class
)
public interface IUserFeignClient {
    @GetMapping("/{id}")
    User getById(@PathVariable("id") Long id);
}