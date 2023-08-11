package com.prueba.prueba.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "profesor")
public class Profesor implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "salario")
    private Integer Salario;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "persona_id")
    @JsonBackReference
    private Person persona;

    public Profesor() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSalario() {
        return Salario;
    }

    public void setSalario(Integer salario) {
        Salario = salario;
    }

    public Person getPersona() {
        return persona;
    }

    public void setPersona(Person person) {
        this.persona = person;
    }

    
}
