package org.example.springdatapractice2.controller;

import org.example.springdatapractice2.entity.Department;
import org.example.springdatapractice2.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/departments")
    public ResponseEntity<List<Department>> getAllDepartments() {
        return new ResponseEntity<>(departmentService.getAllDepartments(), HttpStatus.OK);
    }

    @GetMapping("/departments/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) {
        return new ResponseEntity<>(departmentService.getDepartmentById(id), HttpStatus.OK);
    }

    @PostMapping("/departments")
    public ResponseEntity<Department> createDepartments(@RequestBody Department department) {
        return new ResponseEntity<>(departmentService.createDepartment(department), HttpStatus.CREATED);
    }

    @PutMapping("/departments/{id}")
    public ResponseEntity<Department> updateDepartments(@PathVariable Long id, @RequestBody Department department) {
        return new ResponseEntity<>(departmentService.updateDepartment(id, department), HttpStatus.OK);
    }

    @DeleteMapping("/departments/{id}")
    public ResponseEntity<HttpStatus> deleteDepartmentById(@PathVariable Long id) {
        departmentService.deleteDepartmentById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
