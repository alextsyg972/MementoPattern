package org.example.springsecuritypractice2.service;

import org.example.springsecuritypractice2.dto.ReqRes;
import org.example.springsecuritypractice2.dto.UserDto;
import org.example.springsecuritypractice2.entity.Role;
import org.example.springsecuritypractice2.entity.User;
import org.example.springsecuritypractice2.exception.ResourceNotFoundException;
import org.example.springsecuritypractice2.repository.UserRepository;
import org.example.springsecuritypractice2.security.JWTUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthService {

    private UserRepository userRepository;

    private JWTUtils jwtUtils;

    private PasswordEncoder passwordEncoder;

    private AuthenticationManager authenticationManager;

    private static final Logger logger = LoggerFactory.getLogger("my-logger-name");

    @Autowired
    public AuthService(UserRepository userRepository, JWTUtils jwtUtils, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public UserDto registration(ReqRes registrationRequest) {
        if (userRepository.findByUsername(registrationRequest.getPassword()).isPresent()) {
            throw new ResourceNotFoundException("USER ALREADY EXISTS");
        }
        User user = new User();
        user.setRole(Role.valueOf((registrationRequest.getRole())));
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setUsername(registrationRequest.getUserName());
        User userResult = userRepository.save(user);

        UserDto userdto = UserDto.builder()
                .username(userResult.getUsername())
                .role(userResult.getRole().name())
                .build();
        logger.info("User with username: {} saved", userResult.getUsername());

        return userdto;
    }

    public ReqRes signIn(ReqRes singRequest) {
        ReqRes response = new ReqRes();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(singRequest.getUserName(), singRequest.getPassword()));
        var user = userRepository.findByUsername(singRequest.getUserName()).orElseThrow(() -> new RuntimeException("User not found"));
        var jwt = jwtUtils.generateToken(user);
        var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
        response.setToken(jwt);
        response.setRole(user.getRole().name());
        response.setRefreshToken(refreshToken);
        logger.info("User with username: {} sign in", user.getUsername());

        return response;
    }

    public ReqRes refreshToken(ReqRes refreshTokenRequest) {
        ReqRes response = new ReqRes();
        String username = jwtUtils.extractUsername(refreshTokenRequest.getToken());
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("user not found"));
        if (jwtUtils.isTokenValid(refreshTokenRequest.getToken(), user)) {
            var jwt = jwtUtils.generateToken(user);
            response.setToken(jwt);
            response.setRefreshToken(refreshTokenRequest.getToken());
            logger.info("Token for username: {} refreshed", user.getUsername());
            return response;
        }
        return response;
    }

}
