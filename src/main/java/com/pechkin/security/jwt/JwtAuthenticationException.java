package com.pechkin.security.jwt;

import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationException extends AuthenticationException {
    private static final String ERROR_TEXT = "JWT token is expired or invalid";

    public JwtAuthenticationException() {
        super(ERROR_TEXT);
    }
}
