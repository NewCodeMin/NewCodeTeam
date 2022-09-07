package com.NewCodeTeam.Comercializadora.rest;

import com.NewCodeTeam.Comercializadora.Service.EnterpriseService;
import com.NewCodeTeam.Comercializadora.model.Enterprise;
import com.NewCodeTeam.Comercializadora.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class EnterpriseRest {

    @Autowired
    private EnterpriseService enterpriseService;

    @GetMapping("/enterprises")
    public ResponseEntity<List<Enterprise>> getAllEnterprise (){
        return ResponseEntity.ok(enterpriseService.findAll());
    }

    @GetMapping("/enterprises/{id}")
    public Optional <Enterprise> getEnterpriseById (@PathVariable("id") Long id){
        return this.enterpriseService.findById(id);
    }

    @PostMapping("/enterprises")
    public  ResponseEntity<Enterprise> saveEnterprise (@RequestBody Enterprise enterprise){
        try {
            Enterprise enterpriseSave = enterpriseService.save(enterprise);
            return ResponseEntity.created(new URI("/api/enterprises"+ enterpriseSave.getId())).body(enterpriseSave);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PatchMapping("/enterprises")
    public  ResponseEntity<Enterprise> updateEnterprise (@RequestBody Enterprise enterprise){
        try {
            Enterprise enterpriseSave = enterpriseService.save(enterprise);
            return ResponseEntity.created(new URI("/api/enterprises"+ enterpriseSave.getId())).body(enterpriseSave);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping(value = "/enterprises/{id}")
    public  String deleteEnterprise (@PathVariable("id") Long id){
        boolean answer=enterpriseService.deleteById(id);
        if (answer){
            return "Se pudo eliminar correctamente la empresa con id "+id;
        }else{
            return "No se puedo eliminar correctamente la empresa con id "+id;
        }
    }

    @GetMapping ("enterprises/{id}/movements")
    private ResponseEntity<List<Transaction>> getAllEstadosByPais (@PathVariable("id") Long id){
        return ResponseEntity.ok(enterpriseService.findMovimentsEnterpriseByIdEnterprise(id));
    }

    @PostMapping("/enterprises/{id}/movements")
    public  ResponseEntity<Transaction> saveMovementEnterprise (@PathVariable("id") Long id,@RequestBody Transaction transaction){
        try {
            Transaction transactionSave = enterpriseService.saveTransaction(transaction);
            return ResponseEntity.created(new URI("/api/enterprises/"+id+"/movements"+ transactionSave.getId())).body(transactionSave);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PatchMapping("/enterprises/{id}/movements")
    public  ResponseEntity<Transaction> updateMovementEnterprise (@PathVariable("id") Long id,@RequestBody Transaction transaction){
        try {
            Transaction transactionSave = enterpriseService.saveTransaction(transaction);
            return ResponseEntity.created(new URI("/api/enterprises/"+id+"/movements"+ transactionSave.getId())).body(transactionSave);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping(value = "/enterprises/{idEnterprise}/movements/{id}")
    public  String deleteMovementEnterprise (@PathVariable("id") Long id){
        boolean answer=enterpriseService.deleteMovementById(id);
        if (answer){
            return "Se pudo eliminar correctamente el movimiento de la empresa";
        }else{
            return "No se puedo eliminar correctamente el movimiento de la empresa";
        }
    }
}