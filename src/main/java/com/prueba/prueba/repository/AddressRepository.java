package com.prueba.prueba.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prueba.prueba.entities.Address;

public interface AddressRepository extends JpaRepository<Address, Integer>{
    
}
