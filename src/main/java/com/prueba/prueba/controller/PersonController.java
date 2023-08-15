package com.prueba.prueba.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.prueba.entities.Address;
import com.prueba.prueba.entities.Estudiante;
import com.prueba.prueba.entities.Person;
import com.prueba.prueba.entities.Profesor;
import com.prueba.prueba.repository.AddressRepository;
import com.prueba.prueba.repository.EstudianteRepository;
import com.prueba.prueba.repository.PersonaRepository;
import com.prueba.prueba.repository.ProfesorRepository;
import com.prueba.prueba.request.PersonRequest;

import jakarta.validation.Valid;

@CrossOrigin(origins = { "*" }, methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
        RequestMethod.DELETE })
@RestController
public class PersonController {
    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ProfesorRepository profesorRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @GetMapping("/personas")
    public ResponseEntity<List<Person>> getAllPruebas() {
        List<Person> personas = personaRepository.findByActivo(1);
        return ResponseEntity.ok(personas);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        // Puedes personalizar el mensaje de error y el código de estado aquí
        String errorMessage = "Ocurrió un error en la aplicación.";
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

    @PostMapping
    public Person createPrueba(@RequestBody Person prueba) {
        return personaRepository.save(prueba);
    }

    @PostMapping("/personas")
    public String agregarDireccion(@Valid @RequestBody PersonRequest request, BindingResult result) {

        String response = null;

        if (result.hasErrors()) {

            return result.getFieldError().getDefaultMessage();
        }
        // Buscar a la persona por correo electrónico
        Person existingPerson = personaRepository.findByEmail(request.getEmail());

        if (existingPerson == null) {
            // Si la persona no existe, crea una nueva y agrega la dirección
            Person newPerson = new Person();
            newPerson.setName(request.getName());
            newPerson.setPhoneNumber(request.getPhoneNumber());
            newPerson.setEmail(request.getEmail());
            newPerson.setActivo(request.getActivo());

            // Guardar Person en la base de datos
            Person savedPerson = personaRepository.save(newPerson);

            Address address = new Address();
            address.setStreet(request.getAddress().getStreet());
            address.setCity(request.getAddress().getCity());
            address.setState(request.getAddress().getState());
            address.setPostal_code(Integer.valueOf(request.getAddress().getPostalCode()));
            address.setCountry(request.getAddress().getCountry());

            address.setPerson(savedPerson.getId());
            // Guardar Address en la base de datos
            addressRepository.save(address);

            // Valida el roll de la persona e inserta si es docente o estudiante
            if (request.getTipoRoll() == 1) {
                Estudiante estudiante = new Estudiante();
                estudiante.setCodigoEstudiante(request.getEstudiante().getCodigoEstudiante());
                estudiante.setPromedio(request.getEstudiante().getPromedio());
                estudiante.setPerson(savedPerson);
                estudianteRepository.save(estudiante);

            } else {
                Profesor profesor = new Profesor();
                profesor.setSalario(request.getProfesor().getSalario());
                profesor.setPersona(savedPerson);
                profesorRepository.save(profesor);
            }
            response = "Guardado exitoso";
            return response;
        } else {
            // Si la persona ya existe, agrega la nueva dirección
            Address address = new Address();
            address.setStreet(request.getAddress().getStreet());
            address.setCity(request.getAddress().getCity());
            address.setState(request.getAddress().getState());
            address.setPostal_code(Integer.valueOf(request.getAddress().getPostalCode()));
            address.setCountry(request.getAddress().getCountry());

            address.setPerson(existingPerson.getId());

            addressRepository.save(address);

            response = "Guardado exitoso nueva dirección";
            return response;
        }
    }

    @PutMapping("/personas/{id}")
    public Person updatePersonAndAddress(@PathVariable Integer id, @RequestBody Person updatedPerson) {
        Optional<Person> existingPersonOptional = personaRepository.findById(id);

        if (existingPersonOptional.isPresent()) {
            Person existingPerson = existingPersonOptional.get();

            // Actualiza las propiedades de la Person con los valores de updatedPerson
            existingPerson
                    .setName(updatedPerson.getName() != null ? updatedPerson.getName() : existingPerson.getName());
            existingPerson.setPhoneNumber(updatedPerson.getPhoneNumber() != null ? updatedPerson.getPhoneNumber()
                    : existingPerson.getPhoneNumber());
            existingPerson
                    .setEmail(updatedPerson.getEmail() != null ? updatedPerson.getEmail() : existingPerson.getEmail());

            // Guarda la Person actualizada en la base de datos
            existingPerson = personaRepository.save(existingPerson);

            return existingPerson;
        } else {
            return null; // Manejo de error si el registro no se encuentra
        }
    }

    @DeleteMapping("/personas/{id}")
    public Person deletePrueba(@PathVariable Integer id) {
        Optional<Person> existingPersonOptional = personaRepository.findById(id);

        if (existingPersonOptional.isPresent()) {
            Person existingPerson = existingPersonOptional.get();

            existingPerson.setName(existingPerson.getName());
            existingPerson.setPhoneNumber(existingPerson.getPhoneNumber());
            existingPerson.setEmail(existingPerson.getEmail());
            existingPerson.setActivo(2);
            existingPerson = personaRepository.save(existingPerson);

            return existingPerson;

        } else {
            return null;
        }
    }

}
