package com.prueba.prueba.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prueba.prueba.entities.Estudiante;

public interface EstudianteRepository extends JpaRepository<Estudiante, Integer>{
    
}
