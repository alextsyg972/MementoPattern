package org.example.springsecuritypractice2.config;

import lombok.AllArgsConstructor;
import org.example.springsecuritypractice2.security.PostAuthChecker;
import org.example.springsecuritypractice2.security.PreAuthChecker;
import org.example.springsecuritypractice2.security.filter.JwtAuthenticationFilter;
import org.example.springsecuritypractice2.security.filter.LoggingFilter;
import org.example.springsecuritypractice2.service.impl.UserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailServiceImpl userDetailService;

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private PreAuthChecker preAuthChecker;

    private PostAuthChecker postAuthChecker;

    private final LoggingFilter loggingFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .requiresChannel(channel -> channel.anyRequest().requiresSecure())
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/api/v1/auth/**", "/h2-console/**").permitAll()
                        .requestMatchers("/api/v1/admin/**", "/api/v1/adminuser/**", "/api/v1/user/**").authenticated())
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(loggingFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenticationProvider()).addFilterAt(
                        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.headers(x -> x.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        return httpSecurity.build();
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        daoAuthenticationProvider.setPreAuthenticationChecks(preAuthChecker);
        daoAuthenticationProvider.setPostAuthenticationChecks(postAuthChecker);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
