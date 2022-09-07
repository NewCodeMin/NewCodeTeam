package com.NewCodeTeam.Comercializadora.Service;

import com.NewCodeTeam.Comercializadora.model.Employee;
import com.NewCodeTeam.Comercializadora.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> findAll() {
        return null;
    }

    public List<Employee> findAll(Sort sort) {
        return null;
    }

    public Page<Employee> findAll(Pageable pageable) {
        return null;
    }

    public List<Employee> findAllById(Iterable<Long> iterable) {
        return null;
    }

    public long count() {
        return 0;
    }

    public void deleteById(Long aLong) {

    }

    public void delete(Employee employee) {

    }

    public void deleteAll(Iterable<? extends Employee> iterable) {

    }

    public void deleteAll() {

    }

    public <S extends Employee> S save(S s) {
        return null;
    }

    public <S extends Employee> List<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    public Optional<Employee> findById(Long aLong) {
        return Optional.empty();
    }

    public boolean existsById(Long aLong) {
        return false;
    }

    public void flush() {

    }

    public <S extends Employee> S saveAndFlush(S s) {
        return null;
    }

    public void deleteInBatch(Iterable<Employee> iterable) {

    }

    public void deleteAllInBatch() {

    }

    public Employee getOne(Long aLong) {
        return null;
    }

    public <S extends Employee> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    public <S extends Employee> List<S> findAll(Example<S> example) {
        return null;
    }

    public <S extends Employee> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    public <S extends Employee> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    public <S extends Employee> long count(Example<S> example) {
        return 0;
    }

    public <S extends Employee> boolean exists(Example<S> example) {
        return false;
    }

}