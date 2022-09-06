package com.NewCodeTeam.Comercializadora.rest;



import com.NewCodeTeam.Comercializadora.Service.ProfileService;
import com.NewCodeTeam.Comercializadora.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProfileRest {

    @Autowired
    private ProfileService profileService;

    @GetMapping("/profile")//Ver todos los profile
    private ResponseEntity<List<Profile>> getAllProfile (){
        return ResponseEntity.ok(profileService.findAll());
    }

    @GetMapping(path = "profile/{id}")//Buscar por Id
    public Optional<Profile> profilePorID(@PathVariable("id") Long id) {
        return this.profileService.getProfileById(id);

    }

    @PostMapping("/profile") //Guardar
    public Optional<Profile> guardarProfile(@RequestBody Profile profile){
        return Optional.ofNullable(this.profileService.saveOrUpdateProfile(profile));
    }

    @PatchMapping("/profile/{id}") //Actualizar
    public Profile actualizarProfile(@PathVariable("id") Long id, @RequestBody Profile profile){
        Profile profile1=profileService.getProfileById(id).get();
        profile1.setImage(profile.getImage());
        profile1.setPhone(profile.getPhone());
        profile1.setUser(profile.getUser());
        profile1.setCreatedAt(profile.getCreatedAt());
        profile1.setUpdatedAt(profile.getUpdatedAt());
        return profileService.saveOrUpdateProfile(profile1);
    }

    @DeleteMapping("/profile/{id}")//Eliminar
    public String DeleteProfile(@PathVariable("id") Long id){
        boolean respuesta=profileService.deleteProfile(id);
        if (respuesta){
            return "Se pudo eliminar correctamente el empleado con id "+id;
        }
        return "No se puedo eliminar correctamente el empleado con id "+id;
    }






}
