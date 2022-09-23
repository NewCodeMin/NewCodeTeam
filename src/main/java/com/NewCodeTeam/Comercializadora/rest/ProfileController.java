package com.NewCodeTeam.Comercializadora.rest;



import com.NewCodeTeam.Comercializadora.Service.EmployeeService;
import com.NewCodeTeam.Comercializadora.Service.ProfileService;
import com.NewCodeTeam.Comercializadora.model.Employee;
import com.NewCodeTeam.Comercializadora.model.Enterprise;
import com.NewCodeTeam.Comercializadora.model.Profile;
import com.NewCodeTeam.Comercializadora.model.enumeration.EnumRoleName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/editProfile")
    private String editProfile (Model model, @ModelAttribute("mensaje") String mensaje){
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        String email=auth.getName();
        Employee employee= employeeService.findByEmail(email);
        Profile profile = new Profile();
        if (employee.getProfile() != null){
            profile = profileService.findById(employee.getProfile().getId());
        }
        model.addAttribute("profile",profile);
        model.addAttribute("mensaje",mensaje);
        return "newProfile";
    }

   @PostMapping("/saveProfile")
    public  String saveProfile (@ModelAttribute("profile") Profile profile, RedirectAttributes redirectAttributes){
       Authentication auth= SecurityContextHolder.getContext().getAuthentication();
       String email=auth.getName();
        try {
            Employee employee = employeeService.findByEmail(email);
            if(employee.getProfile() != null){
                profile.setUser(employee);
                profile.setId(employee.getProfile().getId());
                profileService.save(profile);
                redirectAttributes.addFlashAttribute("mensaje","saveOK");
                return "redirect:/api/editProfile/";
            }else{
                profile.setUser(employee);
                profileService.save(profile);
                employee.setProfile(profile);
                employeeService.save(employee);
                redirectAttributes.addFlashAttribute("mensaje","saveOK");
                return "redirect:/api/editProfile/";
            }
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("mensaje","saveError");
            return "redirect:/api/editProfile/";
        }
    }

    @DeleteMapping("/profiles/{id}")//Eliminar
    public String deleteProfile(@PathVariable("id") Long id){
        boolean answer=profileService.deleteByIdProfile(id);
        if (answer){
            return "Se pudo eliminar correctamente el empleado con id "+id;
        }else{
            return "No se puedo eliminar correctamente el empleado con id "+id;
        }
    }
}
