package com.NewCodeTeam.Comercializadora.rest;

import com.NewCodeTeam.Comercializadora.Service.EnterpriseService;
import com.NewCodeTeam.Comercializadora.model.Enterprise;
import com.NewCodeTeam.Comercializadora.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/api")
public class EnterpriseController {

    @Autowired
    private EnterpriseService enterpriseService;

    @GetMapping("/enterprises")
    public String getAllEnterprise(Model model, @ModelAttribute("mensaje") String mensaje){
        List<Enterprise> enterpriseList=enterpriseService.findAll();
        model.addAttribute("listEnterprise",enterpriseList);
        model.addAttribute("mensaje",mensaje);
        return "enterprises";
    }

    @GetMapping("/newEnterprise")
    public String newEnterprise(Model model, @ModelAttribute("mensaje") String mensaje){
        Enterprise emp= new Enterprise();
        model.addAttribute("emp",emp);
        model.addAttribute("mensaje",mensaje);
        return "newEnterprise";
    }

    @PostMapping("/saveEnterprise")
    public String saveEnterprise (Enterprise emp, RedirectAttributes redirectAttributes){
        try {
            enterpriseService.save(emp);
            redirectAttributes.addFlashAttribute("mensaje","saveOK");
            return "redirect:/api/enterprises";
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("mensaje","saveError");
            return "redirect:/api/newEnterprise";
        }
    }

    @GetMapping("/editEnterprise/{id}")
    public String editEnterprise(Model model, @PathVariable("id") Long id, @ModelAttribute("mensaje") String mensaje){
        Enterprise emp= enterpriseService.findById(id);
        model.addAttribute("emp",emp);
        model.addAttribute("mensaje", mensaje);
        return "editEnterprise";
    }

    @PostMapping("/updateEnterprise")
    public  String updateEnterprise (@ModelAttribute("emp") Enterprise emp, RedirectAttributes redirectAttributes){
        try {
            enterpriseService.save(emp);
            redirectAttributes.addFlashAttribute("mensaje","updateOK");
            return "redirect:/api/enterprises";
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("mensaje","updateError");
            return "redirect:/api/editEnterprise/"+emp.getId();
        }
    }

    @GetMapping(value = "/deleteEnterprise/{id}")
    public  String deleteEnterprise (@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        if (enterpriseService.deleteById(id)==true){
            redirectAttributes.addFlashAttribute("mensaje","deleteOK");
        }else{
            redirectAttributes.addFlashAttribute("mensaje", "deleteError");
        }
        return "redirect:/api/enterprises";
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