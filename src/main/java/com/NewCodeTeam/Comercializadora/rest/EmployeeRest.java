package com.NewCodeTeam.Comercializadora.rest;

import com.NewCodeTeam.Comercializadora.Service.EmployeeService;
import com.NewCodeTeam.Comercializadora.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class EmployeeRest {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployee (){
        return ResponseEntity.ok(employeeService.findAll());
    }

    @GetMapping("/employees/{id}")
    public Optional<Employee> getEmployeeById (@PathVariable("id") Long id){
        return this.employeeService.findById(id);
    }

    @PostMapping("/employees")
    public  ResponseEntity<Employee> saveEmployee (@RequestBody Employee employee){
        try {
            Employee employeeSave = employeeService.save(employee);
            return ResponseEntity.created(new URI("/api/employees"+ employeeSave.getId())).body(employeeSave);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PatchMapping("/employees")
    public  ResponseEntity<Employee> updateEmployee (@RequestBody Employee employee){
        try {
            Employee employeeSave = employeeService.save(employee);
            return ResponseEntity.created(new URI("/api/employees"+ employeeSave.getId())).body(employeeSave);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping(value = "/employees/{id}")
    public  String deleteEmployee (@PathVariable("id") Long id){
        boolean answer=employeeService.deleteById(id);
        if (answer){
            return "Se pudo eliminar correctamente el empleado con id "+id;
        }else{
            return "No se puedo eliminar correctamente el empleado con id "+id;
        }
    }
}
