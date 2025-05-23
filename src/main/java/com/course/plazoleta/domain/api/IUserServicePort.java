package com.course.plazoleta.domain.api;

import com.course.plazoleta.domain.model.User;

public interface IUserServicePort {

    User getUserById(Long userId);
}
