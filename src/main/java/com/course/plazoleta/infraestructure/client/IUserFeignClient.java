package com.course.plazoleta.infraestructure.client;

import com.course.plazoleta.domain.model.User;
import com.course.plazoleta.infraestructure.configuration.UserClientFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "users",              // el serviceId en Eureka/Consul de tu users-service
        path = "/users/",             // base path de aquel microservicio
        configuration = UserClientFeignConfig.class  // opcional, para agregar Interceptors
)
public interface IUserFeignClient {
    @GetMapping("/{id}")
    User getById(@PathVariable("id") Long id);
}