package org.example.springsecuritypractice2.controller;

import org.example.springsecuritypractice2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/unlockUser/{username}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<String> unlockUser(@PathVariable String username) {
        userService.unlockUser(username);
        return new ResponseEntity<>("User with username unlocked successfully" + username, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/admin/alone")
    public ResponseEntity<Object> adminAlone() {
        return new ResponseEntity<>("Only admin can access", HttpStatus.OK);
    }
}
