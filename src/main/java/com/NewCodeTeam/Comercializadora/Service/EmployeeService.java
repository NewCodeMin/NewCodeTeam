package com.NewCodeTeam.Comercializadora.Service;

import com.NewCodeTeam.Comercializadora.model.Employee;

import com.NewCodeTeam.Comercializadora.model.enumeration.EnumRoleName;
import com.NewCodeTeam.Comercializadora.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


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
    public boolean saveOrUpdateEmpleado(Employee empl){
        Employee emp=employeeRepository.save(empl);
        if (employeeRepository.findById(emp.getId())!=null){
            return true;
        }
        return false;
    }




}