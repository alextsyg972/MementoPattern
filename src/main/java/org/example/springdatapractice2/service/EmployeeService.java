package org.example.springdatapractice2.service;

import org.example.springdatapractice2.entity.Employee;
import org.example.springdatapractice2.service.projections.EmployeeProjection;

import java.util.List;

public interface EmployeeService {

    List<Employee> getAllEmployees();

    List<EmployeeProjection> getAllByFirstName(String firstName);

    Employee getEmployeeById(Long id);

    Employee createEmployee(Employee employee);

    Employee updateEmployee(Long id, Employee employee);

    void deleteEmployeeById(Long id);

}
