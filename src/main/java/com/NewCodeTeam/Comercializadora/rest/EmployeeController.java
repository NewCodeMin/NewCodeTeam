package com.NewCodeTeam.Comercializadora.rest;

import com.NewCodeTeam.Comercializadora.Service.EmployeeService;
import com.NewCodeTeam.Comercializadora.Service.EnterpriseService;
import com.NewCodeTeam.Comercializadora.model.Employee;
import com.NewCodeTeam.Comercializadora.model.Enterprise;
import com.NewCodeTeam.Comercializadora.model.Transaction;
import com.NewCodeTeam.Comercializadora.model.enumeration.EnumRoleName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EnterpriseService enterpriseService;



    @GetMapping("/employees")
    public String getAllEmployee (Model model, @ModelAttribute("mensaje") String mensaje){
        List<Employee> employeeList= employeeService.findAll();
        model.addAttribute("emplelist",employeeList);
        model.addAttribute("mensaje",mensaje);
        return "employees";
    }

    @GetMapping("/newEmployee")
    public String newEmployee(Model model, @ModelAttribute("mensaje") String mensaje){
        Employee empl= new Employee();
        model.addAttribute("empl",empl);
        model.addAttribute("mensaje",mensaje);
        List<Enterprise> enterpriseList= enterpriseService.findAll();
        model.addAttribute("emprelist",enterpriseList);
        List<EnumRoleName> listRole= new ArrayList<EnumRoleName>(Arrays.asList(EnumRoleName.values()));;
        model.addAttribute("rolelist",listRole);
        return "newEmployee";
    }

    @PostMapping("/saveEmployee")
    public String saveEmployee(Employee empl, RedirectAttributes redirectAttributes){
        String passEncriptada=passwordEncoder().encode(empl.getPassword());
        empl.setPassword(passEncriptada);
        try {
            employeeService.save(empl);
            redirectAttributes.addFlashAttribute("mensaje","saveOK");
            return "redirect:/api/employees";
        }catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "saveError");
            return "redirect:/api/newEmployee";
        }
    }

    @GetMapping("/editEmployee/{id}")
    public String editEmployee(Model model, @PathVariable("id") Long id, @ModelAttribute("mensaje") String mensaje){
        Employee empl= employeeService.findById(id);
        model.addAttribute("empl",empl);
        model.addAttribute("mensaje", mensaje);
        List<Enterprise> enterpriseList= enterpriseService.findAll();
        model.addAttribute("emprelist",enterpriseList);
        List<EnumRoleName> listRole= new ArrayList<EnumRoleName>(Arrays.asList(EnumRoleName.values()));
        model.addAttribute("rolelist",listRole);
        return "editEmployee";
    }

    @PostMapping("/updateEmployee")
    public  String updateEmployee (@ModelAttribute("empl") Employee empl, RedirectAttributes redirectAttributes){
        Employee employee = employeeService.findById(empl.getId());
        empl.setPassword(employee.getPassword());
        try {
            employeeService.save(empl);
            redirectAttributes.addFlashAttribute("mensaje","updateOK");
            return "redirect:/api/employees";
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("mensaje","updateError");
            return "redirect:/api/editEmployee/"+empl.getId();
        }
    }

    @GetMapping(value = "/deleteEmployee/{id}")
    public  String deleteEmployee (@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        if (employeeService.deleteById(id)){
            redirectAttributes.addFlashAttribute("mensaje","deleteOK");
        }else{
            redirectAttributes.addFlashAttribute("mensaje", "deleteError");
        }
        return "redirect:/api/employees";
    }

    @GetMapping("/enterprise/{id}/employees")
    public String getEmployeesByEnterprise (@PathVariable("id") Long id, Model model){
        List<Employee> employeeList = employeeService.findEmployeesByIdEnterprise(id);
        model.addAttribute("emplelist",employeeList);
        return "employees";
    }

    @RequestMapping(value = "/Denegado")
    public String accesoDenegado(){
        return "denegado";
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
