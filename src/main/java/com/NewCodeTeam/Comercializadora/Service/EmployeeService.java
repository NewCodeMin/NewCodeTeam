package com.NewCodeTeam.Comercializadora.Service;

import com.NewCodeTeam.Comercializadora.model.Employee;
import com.NewCodeTeam.Comercializadora.model.Enterprise;
import com.NewCodeTeam.Comercializadora.model.Transaction;
import com.NewCodeTeam.Comercializadora.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public boolean deleteById(Long id) {
        employeeRepository.deleteById(id);
        if (this.employeeRepository.findById(id).isPresent()) {
            return false;
        }
        return true;
    }

    public <S extends Employee> S save(S entity) {
        return employeeRepository.save(entity);
    }

    public Employee findById(Long id) {
        return employeeRepository.findById(id).get();
    }

    public List<Employee> findEmployeesByIdEnterprise(Long id) {
        List<Employee> employees = new ArrayList<>();
        List<Employee> employeesList = employeeRepository.findAll();
        for (Employee employee : employeesList) {
            if (Objects.equals(employee.getEnterprises().getId(), id)) {
                employees.add(employee);
            }
        }
        return employees;
    }
}