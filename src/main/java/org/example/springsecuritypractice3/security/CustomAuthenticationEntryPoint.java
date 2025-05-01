package org.example.springsecuritypractice3.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        logger.warn("401 Unauthorized: Неаутентифицированный доступ к {}. Причина: {}",
                request.getRequestURI(),
                authException.getMessage());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Необходима аутентификация.");
    }
}
