package com.course.plazoleta.infraestructure.output.feing;

import com.course.plazoleta.domain.api.IUserServicePort;
import com.course.plazoleta.domain.exception.UserNotFoundException;
import com.course.plazoleta.domain.model.User;
import com.course.plazoleta.infraestructure.client.IUserFeignClient;
import feign.FeignException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserClientFeignAdapter  implements IUserServicePort {
    private final IUserFeignClient feign;

    @Override
    public User getUserById(Long userId) {
        try {
            return feign.getById(userId);
        } catch (FeignException.NotFound ex) {
            throw new UserNotFoundException(userId);
        }
    }
}
