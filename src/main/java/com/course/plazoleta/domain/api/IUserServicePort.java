package com.course.plazoleta.domain.api;

import com.course.plazoleta.domain.model.feign.User;

public interface IUserServicePort {

    User getUserById(Long userId);
}
