package org.example.springsecuritypractice2.security;

import org.example.springsecuritypractice2.entity.User;
import org.example.springsecuritypractice2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.stereotype.Component;

@Component
public class PostAuthChecker implements UserDetailsChecker {

    private UserRepository userRepository;

    @Autowired
    public PostAuthChecker(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void check(UserDetails toCheck) {
        User user = userRepository.findByUsername(toCheck.getUsername()).orElseThrow();

        user.setAttemptsToLogin(0);
        user.setAccountNonLocked(true);

        userRepository.save(user);
    }
}
