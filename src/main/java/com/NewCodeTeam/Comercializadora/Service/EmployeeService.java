package com.NewCodeTeam.Comercializadora.Service;

import com.NewCodeTeam.Comercializadora.model.Employee;
import com.NewCodeTeam.Comercializadora.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public Optional<Employee> findById(Long id) {
        return employeeRepository.findById(id);
    }

}