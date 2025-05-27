package com.course.plazoleta.infraestructure.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UtilsAdapterTest {

    private final UtilsAdapter utilsAdapter = new UtilsAdapter();

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void getCurrentUserId_returnsNull_whenNoAuthentication() {

        assertNull(utilsAdapter.getCurrentUserId());
    }

    @Test
    void getCurrentUserId_returnsZero_whenPrincipalNotJwt() {
        Authentication auth = mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn("not a jwt");
        SecurityContextHolder.getContext().setAuthentication(auth);

        assertEquals(0L, utilsAdapter.getCurrentUserId());
    }

    @Test
    void getCurrentUserId_returnsLong_whenClaimIsInteger() {
        Authentication auth = mock(Authentication.class);
        Jwt jwt = mock(Jwt.class);

        when(jwt.getClaim("id")).thenReturn(42);
        when(auth.getPrincipal()).thenReturn(jwt);
        SecurityContextHolder.getContext().setAuthentication(auth);

        assertEquals(42L, utilsAdapter.getCurrentUserId());
    }

    @Test
    void getCurrentUserId_returnsLong_whenClaimIsLong() {
        Authentication auth = mock(Authentication.class);
        Jwt jwt = mock(Jwt.class);

        when(jwt.getClaim("id")).thenReturn(123L);
        when(auth.getPrincipal()).thenReturn(jwt);
        SecurityContextHolder.getContext().setAuthentication(auth);

        assertEquals(123L, utilsAdapter.getCurrentUserId());
    }

    @Test
    void getCurrentUserId_returnsZero_whenClaimIsOtherType() {
        Authentication auth = mock(Authentication.class);
        Jwt jwt = mock(Jwt.class);

        when(jwt.getClaim("id")).thenReturn("string");
        when(auth.getPrincipal()).thenReturn(jwt);
        SecurityContextHolder.getContext().setAuthentication(auth);

        assertEquals(0L, utilsAdapter.getCurrentUserId());
    }

}