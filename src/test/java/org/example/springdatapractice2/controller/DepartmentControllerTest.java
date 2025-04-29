package org.example.springdatapractice2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.springdatapractice2.entity.Department;
import org.example.springdatapractice2.service.DepartmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(DepartmentController.class)
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DepartmentService departmentService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void getAllDepartments() throws Exception {
        Department department1 = new Department(1L, "IT");
        Department department2 = new Department(2L, "SALES");

        List<Department> list = List.of(department1, department2);

        when(departmentService.getAllDepartments()).thenReturn(list);
        mockMvc.perform(get("/api/v1/departments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[0].name").value("IT"))
                .andExpect(jsonPath("$.[1].id").value(2))
                .andExpect(jsonPath("$.[1].name").value("SALES"));
        verify(departmentService, times(1)).getAllDepartments();

    }

    @Test
    void getDepartmentById() throws Exception {
        Department department1 = new Department(1L, "IT");

        when(departmentService.getDepartmentById(1L)).thenReturn(department1);

        mockMvc.perform(get("/api/v1/departments/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("IT"));

        verify(departmentService, times(1)).getDepartmentById(1L);

    }

    @Test
    void createDepartments() throws Exception {
        Department department1 = new Department(1L, "IT");

        mockMvc.perform(post("/api/v1/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(department1)))
                .andExpect(status().isCreated());

        verify(departmentService, times(1)).createDepartment(department1);
    }

    @Test
    void updateDepartments() throws Exception {
        Department department = new Department();
        department.setId(1L);
        department.setName("IT");

        mockMvc.perform(put("/api/v1/departments/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(department)))
                .andExpect(status().isOk());

        verify(departmentService, times(1)).updateDepartment(1L, department);

    }

    @Test
    void deleteDepartmentById() throws Exception {
        mockMvc.perform(delete("/api/v1/departments/{id}", 1L))
                .andExpect(status().isNoContent());
        verify(departmentService, times(1)).deleteDepartmentById(1L);

    }
}