package org.example.practice1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.practice1.entity.Order;
import org.example.practice1.entity.OrderStatus;
import org.example.practice1.entity.User;
import org.example.practice1.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void getAllUsers() throws Exception {
        List<User> userList = new ArrayList<>();
        User user = new User();
        user.setId(1);
        user.setName("Alex");
        user.setEmail("tsyg@gmail.com");
        User user1 = new User();
        user1.setId(2);
        user1.setName("Boris");
        user1.setEmail("boris@gmail.com");

        userList.add(user);
        userList.add(user1);

        when(userService.getAllUsers()).thenReturn(userList);

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Alex"))
                .andExpect(jsonPath("$[0].email").value("tsyg@gmail.com"))
                .andExpect(jsonPath("$[1].name").value("Boris"))
                .andExpect(jsonPath("$[1].email").value("boris@gmail.com"));

        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void getUserByIdCorrectData() throws Exception {
        User user = new User();
        user.setId(1);
        user.setName("Alex");
        user.setEmail("tsyg@gmail.com");
        Order order = new Order();
        order.setId(1L);
        order.setOrderStatus(OrderStatus.COMPLETED);
        order.setSum(BigDecimal.valueOf(200));
        List<Order> orders = new ArrayList<>();
        orders.add(order);

        user.setOrders(orders);

        when(userService.getUserById(1L)).thenReturn(user);

        mockMvc.perform(get("/api/v1/users/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Alex"))
                .andExpect(jsonPath("$.email").value("tsyg@gmail.com"))
                .andExpect(jsonPath("$.orders.[0].id").value(1))
                .andExpect(jsonPath("$.orders.[0].orderStatus").value("COMPLETED"))
                .andExpect(jsonPath("$.orders.[0].sum").value(200));

        verify(userService, times(1)).getUserById(1L);
    }


    @Test
    void createUser() throws Exception {
        User user = new User();
        user.setId(1);
        user.setName("Alex");
        user.setEmail("tsyg@gmail.com");


        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated());
    }

    @Test
    void updateUser() throws Exception {
        User user = new User();
        user.setId(1);
        user.setName("Alex");
        user.setEmail("tsyg@gmail.com");

        User user1 = new User();
        user1.setId(2);
        user1.setName("Boris");
        user1.setEmail("enu@gmail.com");
        when(userService.updateUser(1L, user)).thenReturn(user1);


        mockMvc.perform(patch("/api/v1/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Boris"));

        verify(userService, times(1)).updateUser(1L,user);

    }

    @Test
    void deleteUserById() throws Exception {
        mockMvc.perform(delete("/api/v1/users/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).deleteUserById(1L);
    }
}