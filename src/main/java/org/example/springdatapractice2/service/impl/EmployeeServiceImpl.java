package org.example.springdatapractice2.service.impl;

import org.example.springdatapractice2.entity.Employee;
import org.example.springdatapractice2.repository.EmployeeRepository;
import org.example.springdatapractice2.service.EmployeeService;
import org.example.springdatapractice2.service.projections.EmployeeProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    @Override
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(Long id, Employee employee) {
        Employee updated = getEmployeeById(id);
        updated.setDepartment(employee.getDepartment());
        updated.setSalary(employee.getSalary());
        updated.setPosition(employee.getPosition());
        updated.setFirstName(employee.getFirstName());
        updated.setLastName(employee.getLastName());
        return employeeRepository.save(updated);
    }

    @Override
    public void deleteEmployeeById(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public List<EmployeeProjection> getAllByFirstName(String firstName) {
        return employeeRepository.findByFirstName(firstName);
    }
}
