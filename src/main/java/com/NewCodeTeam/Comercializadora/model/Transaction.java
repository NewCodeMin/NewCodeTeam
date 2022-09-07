package com.NewCodeTeam.Comercializadora.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private  String concept;

    private float amount;


    @ManyToOne
    @JsonIgnoreProperties("user")
    @JoinColumn(name="id_user")
    private Employee user;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="id_enterprise")
    private Enterprise enterprises;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    public Transaction() {

    }

    public Transaction(String concept, float amount, Employee user, Enterprise enterprises, LocalDate createdAt, LocalDate updatedAt) {
        this.concept = concept;
        this.amount = amount;
        this.user = user;
        this.enterprises = enterprises;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Employee getUser() {
        return user;
    }

    public void setUser(Employee user) {
        this.user = user;
    }

    public Enterprise getEnterprises() {
        return enterprises;
    }

    public void setEnterprises(Enterprise enterprises) {
        this.enterprises = enterprises;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transaction)) {
            return false;
        }
        return id != null && id.equals(((Transaction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", concept='" + concept + '\'' +
                ", amount=" + amount +
                ", user=" + user +
                ", enterprises=" + enterprises +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
