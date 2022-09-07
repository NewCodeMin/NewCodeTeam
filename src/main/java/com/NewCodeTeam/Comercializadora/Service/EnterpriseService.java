package com.NewCodeTeam.Comercializadora.Service;

import com.NewCodeTeam.Comercializadora.model.Enterprise;
import com.NewCodeTeam.Comercializadora.model.Profile;
import com.NewCodeTeam.Comercializadora.repository.EnterpriseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EnterpriseService {

    @Autowired
    private EnterpriseRepository enterpriseRepository;

    public List<Enterprise> findAll() {
        return enterpriseRepository.findAll();
    }

    public boolean deleteById(Long id) {
        enterpriseRepository.deleteById(id);
        if (this.enterpriseRepository.findById(id).isPresent()) {
            return false;
        }
        return true;
    }

    public <S extends Enterprise> S save(S entity) {
        return enterpriseRepository.save(entity);
    }

    public Optional<Enterprise> findById(Long id) {
        return enterpriseRepository.findById(id);
    }

   /*** public List<Enterprise> findByOne(Long id) {
        List<Enterprise> enterprise = new ArrayList<>();
        List<Enterprise> enterprisesList = enterpriseRepository.findAll();
        for (int i = 0; i < enterprisesList.size(); i++) {
            if(enterprisesList.get(i).getId()== id){
                enterprise.add(enterprisesList.get(i));
                break;
            }
        }
        return enterprise;
    }*/
}