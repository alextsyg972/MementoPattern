package org.example.springdatapractice2.service;

import org.example.springdatapractice2.entity.Department;

import java.util.List;

public interface DepartmentService {

    List<Department> getAllDepartments();

    Department getDepartmentById(Long id);

    Department createDepartment(Department department);

    Department updateDepartment(Long id, Department department);

    void deleteDepartmentById(Long id);

}
