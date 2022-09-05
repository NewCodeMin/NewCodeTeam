package com.NewCodeTeam.Comercializadora.rest;

import com.NewCodeTeam.Comercializadora.Service.EnterpriseService;
import com.NewCodeTeam.Comercializadora.model.Enterprise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EnterpriseRest {

    @Autowired
    private EnterpriseService enterpriseService;

    @GetMapping("/enterprises")
    private ResponseEntity<List<Enterprise>> getAllEnterprise (){
        return ResponseEntity.ok(enterpriseService.findAll());
    }

    @GetMapping("/enterprises/{id}")
    private ResponseEntity <List<Enterprise>> getEnterpriseById (@PathVariable("id") Long id){
        return ResponseEntity.ok(enterpriseService.findByOne(id));
    }

    @PostMapping("/enterprises")
    private  ResponseEntity<Enterprise> saveEnterprise (@RequestBody Enterprise enterprise){
        try {
            Enterprise enterpriseSave = enterpriseService.save(enterprise);
            return ResponseEntity.created(new URI("/api/enterprises"+ enterpriseSave.getId())).body(enterpriseSave);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PatchMapping("/enterprises")
    private  ResponseEntity<Enterprise> updateEnterprise (@RequestBody Enterprise enterprise){
        try {
            Enterprise enterpriseSave = enterpriseService.save(enterprise);
            return ResponseEntity.created(new URI("/api/enterprises"+ enterpriseSave.getId())).body(enterpriseSave);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping(value = "/enterprises/{id}")
    private  ResponseEntity<Boolean> deleteEnterprise (@PathVariable("id") Long id){
        enterpriseService.deleteById(id);
        return ResponseEntity.ok(enterpriseService.findById(id)!= null);
    }
}
