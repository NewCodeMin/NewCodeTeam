package com.NewCodeTeam.Comercializadora.rest;



import com.NewCodeTeam.Comercializadora.Service.EmployeeService;
import com.NewCodeTeam.Comercializadora.Service.ProfileService;
import com.NewCodeTeam.Comercializadora.model.Employee;
import com.NewCodeTeam.Comercializadora.model.Enterprise;
import com.NewCodeTeam.Comercializadora.model.Profile;
import com.NewCodeTeam.Comercializadora.model.enumeration.EnumRoleName;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

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

    public String nameAleatorio (){
        char n;
        Random rnd = new Random();
        String cadena = new String();
        for (int i=0; i < 10 ; i++) {
            n = (char)(rnd.nextDouble() * 26.0 + 65.0 );
            cadena += n; }
        return  cadena;
    }

   @PostMapping("/saveProfile")
    public  String saveProfile (@ModelAttribute("profile") Profile profile, @RequestParam("file") MultipartFile image, RedirectAttributes redirectAttributes){
        String extension = FilenameUtils.getExtension(image.getOriginalFilename());
        String nameAle = nameAleatorio() + "."+extension;
       if(!image.isEmpty()){
           Path directoryImages = Paths.get("src//main//resources//static/imgUs");
           String rutaAbsoluta = directoryImages.toFile().getAbsolutePath();
           try {
               byte[] bytesImg = image.getBytes();
               Path rutaCompleta = Paths.get(rutaAbsoluta + "//"+ nameAle);
               Files.write(rutaCompleta, bytesImg);
           }catch (IOException e){
               e.printStackTrace();
           }
       }
       Authentication auth= SecurityContextHolder.getContext().getAuthentication();
       String email=auth.getName();
        try {
            Employee employee = employeeService.findByEmail(email);
            profile.setImage(nameAle);
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
}
