package org.example.springsecuritypractice3.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal OAuth2User principal, Model model) {
        model.addAttribute("name", principal.getAttribute("name"));
        model.addAttribute("login", principal.getAttribute("login"));
        model.addAttribute("id", principal.getAttribute("id"));
        model.addAttribute("email", principal.getAttribute("email"));
        model.addAttribute("role", principal.getAuthorities());
        return "profile";
    }


}
