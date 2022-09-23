package com.NewCodeTeam.Comercializadora.rest;

import com.NewCodeTeam.Comercializadora.Service.EmployeeService;
import com.NewCodeTeam.Comercializadora.Service.EnterpriseService;
import com.NewCodeTeam.Comercializadora.model.Employee;
import com.NewCodeTeam.Comercializadora.model.Enterprise;
import com.NewCodeTeam.Comercializadora.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/api")
public class EnterpriseController {

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private EmployeeService employeeService;


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

    //Movimientos:

    @GetMapping ("enterprise/{id}/movements")
    public String getAllmovementsByEnterprise (Model model, @PathVariable("id") Long id, @ModelAttribute("mensaje") String mensaje){
        List<Transaction> movimentsList=enterpriseService.findMovimentsEnterpriseByIdEnterprise(id);
        float total = enterpriseService.sumMoviments(id);
        model.addAttribute("listMoviments",movimentsList);
        List<Enterprise> empre= enterpriseService.findByIdList(id);
        model.addAttribute("empre",empre);
        model.addAttribute("mensaje",mensaje);
        model.addAttribute("suma", total);
        return "movementsEnterprise";
    }

    @GetMapping("enterprise/{id}/newMovements")
    public String newMoviments(Model model,@PathVariable("id") Long id, @ModelAttribute("mensaje") String mensaje){
        Transaction movement= new Transaction();
        model.addAttribute("movement",movement);
        model.addAttribute("mensaje",mensaje);
        List<Enterprise> empre= enterpriseService.findByIdList(id);
        model.addAttribute("empre",empre);
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        String email=auth.getName();
        Long idEmployee=enterpriseService.findByEmail(email);
        model.addAttribute("idEmployee",idEmployee);
        return "newMovements";
    }

    @PostMapping("/enterprise/saveMovementEnterprise")
    public  String saveMovementEnterprise (Transaction transaction, RedirectAttributes redirectAttributes){
        try {
            enterpriseService.saveTransaction(transaction);
            redirectAttributes.addFlashAttribute("mensaje","saveOK");
            return "redirect:/api/enterprise/"+transaction.getEnterprises().getId()+"/movements";
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("mensaje","saveError");
            return "redirect:/api/enterprise/"+transaction.getEnterprises().getId()+"/newMovements";
        }
    }

    @GetMapping("/editMovementEnterprise/{id}")
    public String editMovementEnterprise(Model model, @PathVariable("id") Long id, @ModelAttribute("mensaje") String mensaje){
        Transaction mov= enterpriseService.findByIdTransaction(id);
        model.addAttribute("mov",mov);
        model.addAttribute("mensaje", mensaje);
        List<Enterprise> empre= enterpriseService.findByIdList(mov.getEnterprises().getId());
        model.addAttribute("empre",empre);
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        String email=auth.getName();
        Long idEmployee=enterpriseService.findByEmail(email);
        model.addAttribute("idEmployee",idEmployee);
        return "editMovements";
    }

    @PostMapping("/enterprise/updateMovementEnterprise")
    public  String updateMovementEnterprise (@ModelAttribute("mov") Transaction mov,RedirectAttributes redirectAttributes){
        try {
            Transaction transactionObject = enterpriseService.findByIdTransaction(mov.getId());
            if(transactionObject.getUser().getEmail().equals(mov.getUser().getEmail())){
                enterpriseService.saveTransaction(mov);
                redirectAttributes.addFlashAttribute("mensaje","updateOK");
                return "redirect:/api/enterprise/"+mov.getEnterprises().getId()+"/movements";
            }else{
                redirectAttributes.addFlashAttribute("mensaje","updateErrorUsuario");
                return "redirect:/api/editMovementEnterprise/"+mov.getId();
            }
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("mensaje","updateError");
            return "redirect:/api/editMovementEnterprise/"+mov.getId();
        }
    }

    @GetMapping(value = "/enterprise/{idEnterprise}/deleteMovement/{id}")
    public  String deleteMovementEnterprise (@PathVariable("idEnterprise") Long idEnterprise,@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        if (enterpriseService.deleteMovementById(id) == true){
            redirectAttributes.addFlashAttribute("mensaje","deleteOK");
        }else{
            redirectAttributes.addFlashAttribute("mensaje", "deleteError");
        }
        return "redirect:/api/enterprise/"+idEnterprise+"/movements";
    }

    @GetMapping ("/employee/movements")
    public String getAllmovementsByEmployee (Model model, @ModelAttribute("mensaje") String mensaje){
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        String email=auth.getName();
        Employee employee=employeeService.findByEmail(email);
        List<Transaction> movimentsList=enterpriseService.findMovimentsEnterpriseByIdEnterprise(employee.getEnterprises().getId());
        float total = enterpriseService.sumMoviments(employee.getId());
        model.addAttribute("listMoviments",movimentsList);
        List<Enterprise> empre= enterpriseService.findByIdList(employee.getId());
        model.addAttribute("empre",empre);
        model.addAttribute("mensaje",mensaje);
        model.addAttribute("suma", total);
        return "movementsEnterprise";
    }
}