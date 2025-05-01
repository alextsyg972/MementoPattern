package org.example.springsecuritypractice3.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomLogoutSuccessHandler.class);


    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        if (authentication != null && authentication.getName() != null) {
            logger.info("Пользователь '{}' вышел из системы.", authentication.getName());
        } else {
            logger.info("Произведена попытка выхода без активной аутентификации.");
        }

        // Перенаправляем на страницу входа с параметром
        response.sendRedirect("/login?logout");
    }
}
