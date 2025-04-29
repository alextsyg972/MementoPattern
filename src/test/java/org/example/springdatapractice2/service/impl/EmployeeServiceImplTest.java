package org.example.springdatapractice2.service.impl;

import org.example.springdatapractice2.entity.Department;
import org.example.springdatapractice2.entity.Employee;
import org.example.springdatapractice2.entity.Position;
import org.example.springdatapractice2.repository.EmployeeRepository;
import org.example.springdatapractice2.service.projections.EmployeeProjection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllEmployees() {
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

        List<Employee> employees = List.of(employee, employee2);

        when(employeeRepository.findAll()).thenReturn(employees);

        List<Employee> result = employeeService.getAllEmployees();
        assertEquals(employees.get(0), result.get(0));

    }

    @Test
    void getEmployeeById() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("alex");
        employee.setLastName("Tsyg");
        employee.setPosition(Position.MANAGER);
        employee.setSalary(BigDecimal.ONE);
        employee.setDepartment(new Department(1L, "IT"));

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        Employee result = employeeService.getEmployeeById(1L);
        assertEquals("alex", result.getFirstName());
    }

    @Test
    void createEmployee() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("alex");
        employee.setLastName("Tsyg");
        employee.setPosition(Position.MANAGER);
        employee.setSalary(BigDecimal.ONE);
        employee.setDepartment(new Department(1L, "IT"));

        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee result = employeeService.createEmployee(employee);
        assertEquals(employee, result);
    }

    @Test
    void updateEmployee() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("alex");
        employee.setLastName("Tsyg");
        employee.setPosition(Position.MANAGER);
        employee.setSalary(BigDecimal.ONE);
        employee.setDepartment(new Department(1L, "IT"));

        when(employeeRepository.save(employee)).thenReturn(employee);
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        Employee result = employeeService.updateEmployee(1L, employee);
        assertEquals(employee, result);
        verify(employeeRepository, times(1)).save(employee);

    }

    @Test
    void deleteEmployeeById() {
        employeeService.deleteEmployeeById(1L);
        verify(employeeRepository, times(1)).deleteById(1L);
    }

    @Test
    void getAllByFirstName() {
        List<EmployeeProjection> projections = new ArrayList<>();

        when(employeeRepository.findByFirstName("al")).thenReturn(projections);

        List<EmployeeProjection> result = employeeService.getAllByFirstName("al");
        assertEquals(projections, result);
        verify(employeeRepository, times(1)).findByFirstName("al");
    }
}