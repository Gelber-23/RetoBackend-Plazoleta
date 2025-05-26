package com.course.plazoleta.domain.model;

import com.course.plazoleta.domain.model.feign.RoleDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleDtoTest {

    @Test
    void testRoleDtoAllMethods() {

        RoleDto roleDto = new RoleDto();
        roleDto.setId(1);
        roleDto.setName("ADMIN");
        roleDto.setDescription("Administrator role");

        assertEquals(1, roleDto.getId());
        assertEquals("ADMIN", roleDto.getName());
        assertEquals("Administrator role", roleDto.getDescription());


        RoleDto fullRoleDto = new RoleDto(2, "USER", "User role");

        assertEquals(2, fullRoleDto.getId());
        assertEquals("USER", fullRoleDto.getName());
        assertEquals("User role", fullRoleDto.getDescription());
    }

}