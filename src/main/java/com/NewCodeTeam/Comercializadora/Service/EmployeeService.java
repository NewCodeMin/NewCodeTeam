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
public class EmployeeService implements IEmployeeService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

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

    @Override
    public Employee save(Employee employee) {
        String passEncriptada= passwordEncoder.encode(employee.getPassword());
        employee.setPassword(passEncriptada);
        return employeeRepository.save(employee);
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByEmail(username);
        Collection<EnumRoleName> CollectionRole= new ArrayList<EnumRoleName>(Collections.singletonList(employee.getRole()));
        if(employee == null){
            throw new UsernameNotFoundException("Email o password invalidos");
        }
        return new User(employee.getEmail(),employee.getPassword(),mapearAutoridadesRoles(CollectionRole));
    }

    private Collection<? extends GrantedAuthority> mapearAutoridadesRoles(Collection<EnumRoleName> roles){
        return  roles.stream().map(role -> new SimpleGrantedAuthority(role.name())).collect(Collectors.toList());
    }
}