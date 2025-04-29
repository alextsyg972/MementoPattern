package org.example.springdatapractice2.service.impl;

import org.example.springdatapractice2.entity.Department;
import org.example.springdatapractice2.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DepartmentServiceImplTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllDepartments() {
        Department department1 = new Department(1L, "IT");
        Department department2 = new Department(2L, "SALES");

        List<Department> list = List.of(department1, department2);

        when(departmentRepository.findAll()).thenReturn(list);

        List<Department> result = departmentService.getAllDepartments();

        assertEquals(2, result.size());
        verify(departmentRepository, times(1)).findAll();
    }

    @Test
    void getDepartmentById() {
        Department department1 = new Department(1L, "IT");

        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department1));

        Department result = departmentService.getDepartmentById(1L);

        assertEquals(1L, result.getId());
        assertEquals("IT", result.getName());
        verify(departmentRepository, times(1)).findById(1L);

    }

    @Test
    void createDepartment() {
        Department department1 = new Department(1L, "IT");

        when(departmentRepository.save(department1)).thenReturn(department1);

        Department result = departmentService.createDepartment(department1);
        assertEquals(department1, result);
        verify(departmentRepository, times(1)).save(department1);
    }

    @Test
    void updateDepartment() {
        Department department1 = new Department(1L, "IT");

        when(departmentRepository.save(department1)).thenReturn(department1);
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department1));

        Department result = departmentService.updateDepartment(1L, department1);
        assertEquals(department1, result);
        verify(departmentRepository, times(1)).save(department1);
    }

    @Test
    void deleteDepartmentById() {
        departmentService.deleteDepartmentById(1L);
        verify(departmentRepository, times(1)).deleteById(1L);
    }
}