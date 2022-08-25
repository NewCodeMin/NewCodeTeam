package com.NewCodeTeam.Comercializadora.model;

import com.NewCodeTeam.Comercializadora.model.enumeration.EnumRoleName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table (name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;

    private EnumRoleName role;

    @ManyToOne
    @JsonIgnoreProperties("enterprises")
    @JoinColumn(name="id_enterprise")
    private Enterprise enterprises;

    @OneToMany(mappedBy = "user")
    private Set<Transaction> transactions = new HashSet<>();

    private LocalDate updatedAt;

    private LocalDate createdAt;

    public Employee(String email, EnumRoleName role, Enterprise enterprises, Set<Transaction> transactions, LocalDate updatedAt, LocalDate createdAt) {
        this.email = email;
        this.role = role;
        this.enterprises = enterprises;
        this.transactions = transactions;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    public Employee() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EnumRoleName getRole() {
        return role;
    }

    public void setRole(EnumRoleName role) {
        this.role = role;
    }

    public Enterprise getEnterprises() {
        return enterprises;
    }

    public void setEnterprises(Enterprise enterprises) {
        this.enterprises = enterprises;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        return id != null && id.equals(((Employee) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", enterprises=" + enterprises +
                ", transactions=" + transactions +
                ", updatedAt=" + updatedAt +
                ", createdAt=" + createdAt +
                '}';
    }
}
