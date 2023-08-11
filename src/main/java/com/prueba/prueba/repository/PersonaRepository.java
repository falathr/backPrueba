package com.prueba.prueba.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prueba.prueba.entities.Person;

public interface PersonaRepository extends JpaRepository<Person, Integer>{

    Person findByEmail(String email);

    List<Person> findByActivo(Integer activo);
    
}
