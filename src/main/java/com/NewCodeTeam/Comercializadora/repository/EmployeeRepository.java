package com.NewCodeTeam.Comercializadora.repository;

import com.NewCodeTeam.Comercializadora.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    public Employee findByEmail(String email);
}
