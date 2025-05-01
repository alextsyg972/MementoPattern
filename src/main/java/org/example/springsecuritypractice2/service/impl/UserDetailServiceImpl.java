package org.example.springsecuritypractice2.service.impl;

import org.example.springsecuritypractice2.entity.User;
import org.example.springsecuritypractice2.exception.ResourceNotFoundException;
import org.example.springsecuritypractice2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("wrong username"));

        if (!user.isAccountNonLocked()) {
            throw new LockedException("account is locked");
        }
        return user;
    }
}
