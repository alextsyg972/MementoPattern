package org.example.springdatapractice2.service.projections;

import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;

public interface EmployeeProjection {

    long getId();

    @Value("#{target.firstName + ' ' + target.lastName}")
    String getFullName();

    @Value("#{target.department.name}")
    String getDepartmentName();

    String getPosition();

    BigDecimal getSalary();
}
