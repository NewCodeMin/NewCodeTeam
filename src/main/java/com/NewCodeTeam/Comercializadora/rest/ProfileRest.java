package com.NewCodeTeam.Comercializadora.rest;



import com.NewCodeTeam.Comercializadora.Service.ProfileService;
import com.NewCodeTeam.Comercializadora.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProfileRest {

    @Autowired
    private ProfileService profileService;

    @GetMapping("/profiles")//Ver todos los profile
    private ResponseEntity<List<Profile>> getAllProfile (){
        return ResponseEntity.ok(profileService.findAll());
    }

    @GetMapping(path = "profiles/{id}")//Buscar por Id
    public Optional<Profile> getProfileByID(@PathVariable("id") Long id) {
        return this.profileService.getProfileById(id);

    }

    @PostMapping("/profiles") //Guardar
    public Optional<Profile> saveProfile(@RequestBody Profile profile){
        return Optional.ofNullable(this.profileService.saveProfile(profile));
    }

    @PatchMapping("/profiles") //Actualizar
    private  ResponseEntity<Profile> updateProfile (@RequestBody Profile profile){
        try {
            Profile profileSave = profileService.updateProfile(profile);
            return ResponseEntity.created(new URI("/api/profiles"+ profileSave.getId())).body(profileSave);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
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
