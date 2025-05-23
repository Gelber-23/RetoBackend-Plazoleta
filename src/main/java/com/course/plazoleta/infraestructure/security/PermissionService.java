package com.course.plazoleta.infraestructure.security;

import com.course.plazoleta.infraestructure.output.jpa.repository.IDishRepository;
import com.course.plazoleta.infraestructure.output.jpa.repository.IRestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class PermissionService {
    private static final String ROLE_ADMIN= "ROLE_1";
    private static final String ROLE_OWNER= "ROLE_2";

    private final IRestaurantRepository restaurantRepository;
    private final IDishRepository dishRepository;

    public boolean isAdmin(Authentication auth) {
        return auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(ROLE_ADMIN));
    }

    public boolean isAdminOrOwner(Authentication auth) {
        return auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(ROLE_ADMIN) || a.getAuthority().equals(ROLE_OWNER));
    }
    public boolean isOwner(Authentication auth) {
        return auth.getAuthorities().stream()
                .anyMatch(a ->  a.getAuthority().equals(ROLE_OWNER));
    }


    public boolean isOwnerOfRestaurant(Authentication auth, Long restaurantId) {
        if (!isOwner(auth)) {
            return false;
        }

        return extractUserId(auth)
                .flatMap(userId ->
                        restaurantRepository.findById(restaurantId)
                                .map(rest -> rest.getId_owner() == userId)
                )
                .orElse(false);
    }
    public boolean isOwnerOfDish(Authentication auth, long dishId) {
        if (!isOwner(auth)) {
            return false;
        }

        return extractUserId(auth)
                .flatMap(userId ->
                        dishRepository.findById(dishId)
                                .map(d -> d.getIdRestaurant().getId_owner()== userId)
                )
                .orElse(false);
    }

    private Optional<Long> extractUserId(Authentication auth) {
        Object principal = auth.getPrincipal();
        if (principal instanceof Jwt jwt) {
            return Optional.ofNullable(jwt.getClaim("id"));
        }
        return Optional.empty();
    }
}
