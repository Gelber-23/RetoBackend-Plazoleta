package com.course.plazoleta.domain.model;

import com.course.plazoleta.domain.model.feign.RoleDto;
import com.course.plazoleta.domain.model.feign.User;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test
    void testUserAllGettersAndSetters() {
        RoleDto initialRole = new RoleDto(1, "ADMIN","ROLE");
        Date birthdate = new Date();

        // Constructor
        User user = new User(1L, "Alice", "Smith", "123456789", "555-1234", birthdate,
                "alice@example.com", "securePassword", initialRole,1l);

        // Getters
        assertEquals(1L, user.getId());
        assertEquals("Alice", user.getName());
        assertEquals("Smith", user.getLastName());
        assertEquals("123456789", user.getDocumentNumber());
        assertEquals("555-1234", user.getPhone());
        assertEquals(birthdate, user.getBirthdate());
        assertEquals("alice@example.com", user.getEmail());
        assertEquals("securePassword", user.getPassword());
        assertEquals(initialRole, user.getRol());
        assertEquals(1l, user.getIdRestaurant());
        // Setters with new values
        RoleDto newRole = new RoleDto(2, "USER","ROLE");
        Date newBirthdate = new Date(birthdate.getTime() + 10000);

        user.setId(2L);
        user.setName("Bob");
        user.setLastName("Johnson");
        user.setDocumentNumber("987654321");
        user.setPhone("555-5678");
        user.setBirthdate(newBirthdate);
        user.setEmail("bob@example.com");
        user.setPassword("newPassword");
        user.setRol(newRole);
        user.setIdRestaurant(2L);
        // Re-assert
        assertEquals(2L, user.getId());
        assertEquals("Bob", user.getName());
        assertEquals("Johnson", user.getLastName());
        assertEquals("987654321", user.getDocumentNumber());
        assertEquals("555-5678", user.getPhone());
        assertEquals(newBirthdate, user.getBirthdate());
        assertEquals("bob@example.com", user.getEmail());
        assertEquals("newPassword", user.getPassword());
        assertEquals(newRole, user.getRol());

        assertEquals(2L, user.getIdRestaurant());
    }

}