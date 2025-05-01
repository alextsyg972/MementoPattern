package org.example.springsecuritypractice2.service.impl;

import org.example.springsecuritypractice2.entity.User;
import org.example.springsecuritypractice2.exception.NotLockedException;
import org.example.springsecuritypractice2.exception.ResourceNotFoundException;
import org.example.springsecuritypractice2.repository.UserRepository;
import org.example.springsecuritypractice2.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger("my-logger-name");

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void unlockUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User with this username not found"));
        if (user.isAccountNonLocked()) {
            throw new NotLockedException("Account already unlocked");
        }
        user.setAccountNonLocked(true);
        user.setAttemptsToLogin(0);
        userRepository.save(user);
        logger.info("Account with username: {} + was unlocked", user.getUsername());
    }
}
