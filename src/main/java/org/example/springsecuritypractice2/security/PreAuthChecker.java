package org.example.springsecuritypractice2.security;

import org.example.springsecuritypractice2.entity.User;
import org.example.springsecuritypractice2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.stereotype.Component;

@Component
public class PreAuthChecker implements UserDetailsChecker {

    private UserRepository userRepository;

    @Autowired
    public PreAuthChecker(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void check(UserDetails toCheck) {
        String username = toCheck.getUsername();
        User user = userRepository.findByUsername(username).orElseThrow();

        user.setAttemptsToLogin(user.getAttemptsToLogin() + 1);

        if (user.getAttemptsToLogin() >= 5) {
            user.setAccountNonLocked(false);
        }
        userRepository.save(user);
    }
}
