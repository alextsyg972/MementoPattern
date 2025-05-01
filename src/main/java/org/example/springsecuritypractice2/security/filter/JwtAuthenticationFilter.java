package org.example.springsecuritypractice2.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.example.springsecuritypractice2.security.JWTUtils;
import org.example.springsecuritypractice2.service.impl.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JWTUtils jwtUtils;

    private UserDetailServiceImpl userDetailService;

    @Autowired
    public JwtAuthenticationFilter(JWTUtils jwtUtils, UserDetailServiceImpl userDetailService) {
        this.jwtUtils = jwtUtils;
        this.userDetailService = userDetailService;
    }

    // Метод, выполняемый для каждого HTTP запроса
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        // Шаг 1: Извлечение заголовка авторизации из запроса
        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String userName;
        // Шаг 2: Проверка наличия заголовка авторизации
        if (authHeader == null || authHeader.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }
        // Шаг 3: Извлечение токена из заголовка
        jwtToken = authHeader.substring(7);
        // Шаг 4: Извлечение имени пользователя из JWT токена
        userName = jwtUtils.extractUsername(jwtToken);
        // Шаг 5: Проверка валидности токена и аутентификации
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailService.loadUserByUsername(userName);
            if (jwtUtils.isTokenValid(jwtToken, userDetails)) {
                // Шаг 6: Создание нового контекста безопасности
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                securityContext.setAuthentication(token);
                SecurityContextHolder.setContext(securityContext);
            }
        }
        // Шаг 7: Передача запроса на дальнейшую обработку в фильтрующий цепочке
        filterChain.doFilter(request, response);
    }


}
