package com.NewCodeTeam.Comercializadora.Service;

import com.NewCodeTeam.Comercializadora.model.Employee;

import com.NewCodeTeam.Comercializadora.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployeeService  {


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

    public Employee findByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }
}