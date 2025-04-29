package org.example.springdatapractice2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.springdatapractice2.entity.Department;
import org.example.springdatapractice2.entity.Employee;
import org.example.springdatapractice2.entity.Position;
import org.example.springdatapractice2.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockitoBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllEmployees() throws Exception {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("alex");
        employee.setLastName("Tsyg");
        employee.setPosition(Position.MANAGER);
        employee.setSalary(BigDecimal.ONE);
        employee.setDepartment(new Department(1L, "IT"));


        Employee employee2 = new Employee();
        employee2.setId(1L);
        employee2.setFirstName("two");
        employee2.setLastName("lastTwo");
        employee2.setPosition(Position.MANAGER);
        employee2.setSalary(BigDecimal.TEN);
        employee2.setDepartment(new Department(1L, "IT"));

        List<Employee> list = List.of(employee, employee2);

        when(employeeService.getAllEmployees()).thenReturn(list);

        mockMvc.perform(get("/api/v1/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[0].firstName").value("alex"))
                .andExpect(jsonPath("$.[0].lastName").value("Tsyg"))
                .andExpect(jsonPath("$.[0].salary").value(1));
        verify(employeeService, times(1)).getAllEmployees();

    }

    @Test
    void getEmployeeById() throws Exception {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("alex");
        employee.setLastName("Tsyg");
        employee.setPosition(Position.MANAGER);
        employee.setSalary(BigDecimal.ONE);
        employee.setDepartment(new Department(1L, "IT"));

        when(employeeService.getEmployeeById(1L)).thenReturn(employee);

        mockMvc.perform(get("/api/v1/employees/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("alex"))
                .andExpect(jsonPath("$.lastName").value("Tsyg"))
                .andExpect(jsonPath("$.salary").value(1));
        verify(employeeService, times(1)).getEmployeeById(1L);

    }

    @Test
    void getEmployeesByFirstName() throws Exception {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("alex");
        employee.setLastName("Tsyg");
        employee.setPosition(Position.MANAGER);
        employee.setSalary(BigDecimal.ONE);
        employee.setDepartment(new Department(1L, "IT"));


        mockMvc.perform(get("/api/v1/employees/by").param("firstName", employee.getFirstName())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(employeeService, times(1)).getAllByFirstName("alex");
    }

    @Test
    void createEmployee() throws Exception {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("alex");
        employee.setLastName("Tsyg");
        employee.setPosition(Position.MANAGER);
        employee.setSalary(BigDecimal.ONE);
        employee.setDepartment(new Department(1L, "IT"));

        mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isCreated());

        verify(employeeService, times(1)).createEmployee(employee);

    }

    @Test
    void updateEmployee() throws Exception {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("alex");
        employee.setLastName("Tsyg");
        employee.setPosition(Position.MANAGER);
        employee.setSalary(BigDecimal.ONE);
        employee.setDepartment(new Department(1L, "IT"));

        mockMvc.perform(put("/api/v1/employees/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isOk());

        verify(employeeService, times(1)).updateEmployee(1L, employee);
    }

    @Test
    void deleteEmployeeById() throws Exception {
        mockMvc.perform(delete("/api/v1/employees/{id}", 1))
                .andExpect(status().isNoContent());
        verify(employeeService, times(1)).deleteEmployeeById(1L);
    }
}