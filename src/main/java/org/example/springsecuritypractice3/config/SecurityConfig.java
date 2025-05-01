package org.example.springsecuritypractice3.config;

import org.example.springsecuritypractice3.security.CustomAccessDeniedHandler;
import org.example.springsecuritypractice3.security.CustomAuthenticationEntryPoint;
import org.example.springsecuritypractice3.security.CustomLogoutSuccessHandler;
import org.example.springsecuritypractice3.service.CustomOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private CustomLogoutSuccessHandler customLogoutSuccessHandler;
    private CustomAccessDeniedHandler customAccessDeniedHandler;
    private CustomAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    public SecurityConfig(CustomLogoutSuccessHandler customLogoutSuccessHandler, CustomAccessDeniedHandler customAccessDeniedHandler, CustomAuthenticationEntryPoint authenticationEntryPoint) {
        this.customLogoutSuccessHandler = customLogoutSuccessHandler;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/", "/login", "/error", "/css/**").permitAll()
                        .requestMatchers("/h2-console/*").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler)
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(new CustomOauth2UserService()))
                        .loginPage("/login")
                        .defaultSuccessUrl("/profile"))
                .logout(logout -> logout
                        .logoutUrl("/logout") // URL для выхода
                        .logoutSuccessHandler(customLogoutSuccessHandler)
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID"))
                .build();

    }

}
