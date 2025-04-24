package org.example.practice1.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.practice1.controller.UserController;
import org.example.practice1.entity.User;
import org.example.practice1.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
class UserExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void handleUserNotFoundException() throws Exception {
        when(userService.getUserById(1L)).thenThrow(new UserNotFoundException("user with this id not found"));

        mockMvc.perform(get("/api/v1/users/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("user with this id not found"));
    }

    @Test
    void handleMethodArgumentNotValid() throws Exception {
        User user = new User();
        user.setId(1);
        user.setName("Alex");
        user.setEmail("tsyggmail.com");

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(jsonPath("$.errors").exists())
                .andExpect(jsonPath("$.errors.email").value("must be a well-formed email address"))
                .andExpect(status().isBadRequest());
    }
}