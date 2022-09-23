package com.NewCodeTeam.Comercializadora.repository;

import com.NewCodeTeam.Comercializadora.model.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EnterpriseRepository extends JpaRepository<Enterprise,Long> {
    @Query(value="select id from employee where email=?1", nativeQuery = true)
    public abstract Long findByEmail(String email);
}
