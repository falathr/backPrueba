package com.prueba.prueba.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.prueba.entities.Address;
import com.prueba.prueba.repository.AddressRepository;

@RestController
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressRepository addressRepository;

    @GetMapping
    public List<Address> getAllPruebas() {
        return addressRepository.findAll();
    }

    @PostMapping
    public Address createPrueba(@RequestBody Address prueba) {
        return addressRepository.save(prueba);
    }

    @PutMapping("/{id}")
    public Address updatePrueba(@PathVariable Integer id, @RequestBody Address updatedPrueba) {
        Optional<Address> existingPruebaOptional = addressRepository.findById(id);

        if (existingPruebaOptional.isPresent()) {
            Address existingPrueba = existingPruebaOptional.get();
            BeanUtils.copyProperties(updatedPrueba, existingPrueba, "id");
            return addressRepository.save(existingPrueba);
        } else {
            return null; // Manejo de error si el registro no se encuentra
        }
    }

    @DeleteMapping("/{id}")
    public void deletePrueba(@PathVariable Integer id) {
        addressRepository.deleteById(id);
    }

}
