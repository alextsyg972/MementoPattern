package org.example.springsecuritypractice2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class UserController {

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/alone")
    public ResponseEntity<Object> userAlone() {
        return new ResponseEntity<>("Only users can access", HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('USER','MODERATOR', 'SUPER_ADMIN')")
    @GetMapping("/adminuser/both")
    public ResponseEntity<Object> bothAdminAndUser() {
        return new ResponseEntity<>("Both admin and user can access", HttpStatus.OK);
    }

}
