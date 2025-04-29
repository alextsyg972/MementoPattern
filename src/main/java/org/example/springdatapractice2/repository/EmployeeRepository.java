package org.example.springdatapractice2.repository;

import org.example.springdatapractice2.entity.Employee;
import org.example.springdatapractice2.service.projections.EmployeeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<EmployeeProjection> findByFirstName(String firstName);

}
