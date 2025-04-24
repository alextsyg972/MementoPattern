package org.example.practice1.service;

import org.example.practice1.entity.User;
import org.example.practice1.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void getAllUsers() {
        User user = new User();
        user.setName("alex");
        List<User> list = new ArrayList<>();
        list.add(user);
        when(userRepository.findAll()).thenReturn(list);


        List<User> result = userService.getAllUsers();
        assertEquals(list.get(0), result.get(0));
    }

    @Test
    void getUserById() {
        User user = new User();
        user.setName("alex");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);
        assertEquals("alex", result.getName());
    }

    @Test
    void createUser() {
        User user = new User();
        user.setName("alex");
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.createUser(user);
        assertEquals(user, result);
    }

    @Test
    void updateUser() {
        User user = new User();
        user.setName("alex");
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.createUser(user);
        assertEquals(user, result);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void deleteUserById() {
        userService.deleteUserById(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }
}