package com.course.plazoleta.infraestructure.utils;

import com.course.plazoleta.domain.api.IUtilsServicePort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class UtilsAdapter implements IUtilsServicePort {

    @Override
    public Long  getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return null;
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof Jwt jwt) {

            Object idClaim = jwt.getClaim("id");
            if (idClaim instanceof Integer intId) {
                return intId.longValue();
            }
            if (idClaim instanceof Long longId) {
                return longId;
            }
        }

        return 0L;
    }
}
