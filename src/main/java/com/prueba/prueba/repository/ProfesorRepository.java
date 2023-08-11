package com.prueba.prueba.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prueba.prueba.entities.Profesor;

public interface ProfesorRepository extends JpaRepository<Profesor, Integer>{
    
}
