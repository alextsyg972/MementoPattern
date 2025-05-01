package org.example.springsecuritypractice2.controller;

import org.example.springsecuritypractice2.dto.ReqRes;
import org.example.springsecuritypractice2.dto.UserDto;
import org.example.springsecuritypractice2.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/registration")
    public ResponseEntity<UserDto> signUp(@RequestBody ReqRes signUpRequest) {
        return new ResponseEntity<>(authService.registration(signUpRequest), HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<ReqRes> signIn(@RequestBody ReqRes signInRequest) {
        return new ResponseEntity<>(authService.signIn(signInRequest), HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ReqRes> refreshToken(@RequestBody ReqRes refreshTokenRequest) {
        return new ResponseEntity<>(authService.refreshToken(refreshTokenRequest), HttpStatus.OK);
    }

}
