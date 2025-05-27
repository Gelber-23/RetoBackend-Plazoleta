package com.course.plazoleta.infraestructure.security;

import com.course.plazoleta.domain.utils.constants.ValuesConstants;
import com.course.plazoleta.infraestructure.output.jpa.repository.IDishRepository;
import com.course.plazoleta.infraestructure.output.jpa.repository.IRestaurantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PermissionServiceTest {

    @Mock
    private IRestaurantRepository restaurantRepository;
    @Mock
    private IDishRepository dishRepository;
    @Mock
    private Authentication authentication;
    @InjectMocks
    private PermissionService permissionService;

    @Test
    void isAdmin_trueFalse() {
        List<SimpleGrantedAuthority> adminAuth = List.of(
                new SimpleGrantedAuthority(ValuesConstants.ROLE_STRING_VALUE_ADMIN)
        );
        doReturn(adminAuth).when(authentication).getAuthorities();
        assertTrue(permissionService.isAdmin(authentication));

        List<SimpleGrantedAuthority> otherAuth = List.of(
                new SimpleGrantedAuthority("ROLE_OTHER")
        );
        doReturn(otherAuth).when(authentication).getAuthorities();
        assertFalse(permissionService.isAdmin(authentication));
    }

    @Test
    void isAdminOrOwner_trueFalse() {

        doReturn(List.of(new SimpleGrantedAuthority(ValuesConstants.ROLE_STRING_VALUE_ADMIN)))
                .when(authentication).getAuthorities();
        assertTrue(permissionService.isAdminOrOwner(authentication));


        doReturn(List.of(new SimpleGrantedAuthority(ValuesConstants.ROLE_STRING_VALUE_OWNER)))
                .when(authentication).getAuthorities();
        assertTrue(permissionService.isAdminOrOwner(authentication));


        doReturn(List.of(new SimpleGrantedAuthority(ValuesConstants.ROLE_STRING_VALUE_CLIENT)))
                .when(authentication).getAuthorities();
        assertFalse(permissionService.isAdminOrOwner(authentication));
    }

    @Test
    void isOwner_clientOrOther() {
        doReturn(List.of(new SimpleGrantedAuthority(ValuesConstants.ROLE_STRING_VALUE_OWNER)))
                .when(authentication).getAuthorities();
        assertTrue(permissionService.isOwner(authentication));

        doReturn(List.of(new SimpleGrantedAuthority(ValuesConstants.ROLE_STRING_VALUE_CLIENT)))
                .when(authentication).getAuthorities();
        assertFalse(permissionService.isOwner(authentication));
    }

    @Test
    void isClient_employeeOrClient() {
        doReturn(List.of(new SimpleGrantedAuthority(ValuesConstants.ROLE_STRING_VALUE_CLIENT)))
                .when(authentication).getAuthorities();
        assertTrue(permissionService.isClient(authentication));

        doReturn(List.of(new SimpleGrantedAuthority(ValuesConstants.ROLE_STRING_VALUE_EMPLOYEE)))
                .when(authentication).getAuthorities();
        assertFalse(permissionService.isClient(authentication));
    }

    @Test
    void isEmployee_clientOrEmployee() {
        doReturn(List.of(new SimpleGrantedAuthority(ValuesConstants.ROLE_STRING_VALUE_EMPLOYEE)))
                .when(authentication).getAuthorities();
        assertTrue(permissionService.isEmployee(authentication));

        doReturn(List.of(new SimpleGrantedAuthority(ValuesConstants.ROLE_STRING_VALUE_OWNER)))
                .when(authentication).getAuthorities();
        assertFalse(permissionService.isEmployee(authentication));
    }

    @Test
    void isOwnerOfRestaurant_notOwnerRole() {
        doReturn(List.of(new SimpleGrantedAuthority(ValuesConstants.ROLE_STRING_VALUE_CLIENT)))
                .when(authentication).getAuthorities();
        assertFalse(permissionService.isOwnerOfRestaurant(authentication, 123L));
        verifyNoInteractions(restaurantRepository);
    }

    @Test
    void isOwnerOfRestaurant_ownerRole_noRecord() {
        Jwt jwt = mock(Jwt.class);
        doReturn(jwt).when(authentication).getPrincipal();
        doReturn(1L).when(jwt).getClaim("id");
        doReturn(List.of(new SimpleGrantedAuthority(ValuesConstants.ROLE_STRING_VALUE_OWNER)))
                .when(authentication).getAuthorities();
        doReturn(Optional.empty()).when(restaurantRepository).findById(5L);

        assertFalse(permissionService.isOwnerOfRestaurant(authentication, 5L));
    }

    @Test
    void isOwnerOfRestaurant_ownerRole_mismatchAndMatch() {
        Jwt jwt = mock(Jwt.class);
        doReturn(jwt).when(authentication).getPrincipal();
        doReturn(5L).when(jwt).getClaim("id");
        doReturn(List.of(new SimpleGrantedAuthority(ValuesConstants.ROLE_STRING_VALUE_OWNER)))
                .when(authentication).getAuthorities();

        var rest = new com.course.plazoleta.infraestructure.output.jpa.entity.RestaurantEntity();
        rest.setId_owner(99L);
        doReturn(Optional.of(rest)).when(restaurantRepository).findById(10L);
        assertFalse(permissionService.isOwnerOfRestaurant(authentication, 10L));

        rest.setId_owner(5L);
        doReturn(Optional.of(rest)).when(restaurantRepository).findById(10L);
        assertTrue(permissionService.isOwnerOfRestaurant(authentication, 10L));
    }

    @Test
    void isOwnerOfDish_notOwnerRole() {
        doReturn(List.of(new SimpleGrantedAuthority(ValuesConstants.ROLE_STRING_VALUE_CLIENT)))
                .when(authentication).getAuthorities();
        assertFalse(permissionService.isOwnerOfDish(authentication, 7L));
        verifyNoInteractions(dishRepository);
    }

    @Test
    void isOwnerOfDish_ownerRole_noRecord() {
        Jwt jwt = mock(Jwt.class);
        doReturn(jwt).when(authentication).getPrincipal();
        doReturn(2L).when(jwt).getClaim("id");
        doReturn(List.of(new SimpleGrantedAuthority(ValuesConstants.ROLE_STRING_VALUE_OWNER)))
                .when(authentication).getAuthorities();
        doReturn(Optional.empty()).when(dishRepository).findById(3L);

        assertFalse(permissionService.isOwnerOfDish(authentication, 3L));
    }

    @Test
    void isOwnerOfDish_ownerRole_mismatchAndMatch() {
        Jwt jwt = mock(Jwt.class);
        doReturn(jwt).when(authentication).getPrincipal();
        doReturn(8L).when(jwt).getClaim("id");
        doReturn(List.of(new SimpleGrantedAuthority(ValuesConstants.ROLE_STRING_VALUE_OWNER)))
                .when(authentication).getAuthorities();

        var dish = new com.course.plazoleta.infraestructure.output.jpa.entity.DishEntity();
        var rest = new com.course.plazoleta.infraestructure.output.jpa.entity.RestaurantEntity();
        rest.setId_owner(8L);
        dish.setIdRestaurant(rest);
        doReturn(Optional.of(dish)).when(dishRepository).findById(3L);
        assertTrue(permissionService.isOwnerOfDish(authentication, 3L));

        rest.setId_owner(99L);
        dish.setIdRestaurant(rest);
        doReturn(Optional.of(dish)).when(dishRepository).findById(3L);
        assertFalse(permissionService.isOwnerOfDish(authentication, 3L));
    }

    @Test
    void extractUserId_nonJwtPrincipal() throws Exception {
        doReturn("stringPrincipal").when(authentication).getPrincipal();

        Method m = PermissionService.class.getDeclaredMethod("extractUserId", Authentication.class);
        m.setAccessible(true);
        Optional<?> out = (Optional<?>) m.invoke(permissionService, authentication);
        assertTrue(out.isEmpty());
    }
}