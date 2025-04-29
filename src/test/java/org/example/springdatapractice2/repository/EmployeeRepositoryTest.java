package org.example.springdatapractice2.repository;

import org.example.springdatapractice2.service.projections.EmployeeProjection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertLinesMatch;

@SpringBootTest
class EmployeeRepositoryTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @Test
    void findByFirstName() {
        List<EmployeeProjection> list = employeeRepository.findByFirstName("FOURTH");

        List expected = List.of("FOURTH bb");

        List actual = list.stream().map(EmployeeProjection::getFullName).collect(Collectors.toList());

        assertLinesMatch(expected, actual);

    }
}