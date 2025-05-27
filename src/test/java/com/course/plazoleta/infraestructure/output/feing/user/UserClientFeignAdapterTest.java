package com.course.plazoleta.infraestructure.output.feing.user;

import com.course.plazoleta.domain.exception.usersexception.UserNotFoundException;
import com.course.plazoleta.domain.model.feign.User;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserClientFeignAdapterTest {
    @Mock
    private IUserFeignClient feign;

    @InjectMocks
    private UserClientFeignAdapter adapter;

    @Test
    void getUserById_shouldReturnUser_whenFeignReturnsUser() {
        Long userId = 1L;
        User expectedUser = new User();
        when(feign.getById(userId)).thenReturn(expectedUser);

        User result = adapter.getUserById(userId);

        assertEquals(expectedUser, result);
        verify(feign).getById(userId);
    }

    @Test
    void getUserById_shouldThrowUserNotFoundException_whenFeignThrowsNotFound() {
        Long userId = 2L;
        when(feign.getById(userId)).thenThrow(FeignException.NotFound.class);

        assertThrows(UserNotFoundException.class, () -> adapter.getUserById(userId));
        verify(feign).getById(userId);
    }

}