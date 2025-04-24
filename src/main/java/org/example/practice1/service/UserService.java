package org.example.practice1.service;

import org.example.practice1.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(Long id);

    User createUser(User user);

    User updateUser(Long id,User user);

    void deleteUserById(Long id);




}
