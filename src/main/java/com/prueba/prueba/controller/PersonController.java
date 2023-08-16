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
import com.prueba.prueba.response.salidaResponse;

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

    // Mapeo para la solicitud GET en la ruta "/personas"
    @GetMapping("/personas")
    public ResponseEntity<List<Person>> getAllPruebas() {
        // Obtener una lista de personas activas desde el repositorio
        List<Person> personas = personaRepository.findByActivo(1);

        // Devuelve una respuesta con la lista de personas en el cuerpo de la respuesta
        // y un código de estado 200 (OK)
        return ResponseEntity.ok(personas);
    }

    // Anotación para manejar excepciones de tipo Exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        // En este método se manejan excepciones de tipo Exception

        // Puede personalizar el mensaje de error y el código de estado aquí
        String errorMessage = "Ocurrió un error en la aplicación.";

        // Devuelve una respuesta con un código de estado 500 (INTERNAL_SERVER_ERROR)
        // y el mensaje de error personalizado en el cuerpo de la respuesta
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

    // Mapeo para la solicitud POST en la ruta "/personas"
    @PostMapping("/personas")
    public salidaResponse agregarDireccion(@Valid @RequestBody PersonRequest request, BindingResult result) {
        // Este método se maneja la solicitud POST para agregar una dirección a una
        // persona

        salidaResponse response = new salidaResponse();
         // Variable para almacenar la respuesta

        // Validar si hay errores en el objeto de solicitud utilizando la anotación
        // @Valid
        // y capturar los resultados de la validación en el objeto BindingResult
        if (result.hasErrors()) {
            // Si hay errores, devuelve el mensaje de error predeterminado del primer campo
            // con error
            response.setCodigoRespuesta("999");
            response.setDescripcion(result.getFieldError().getDefaultMessage().toString());
            return response;
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
            

            response.setCodigoRespuesta("000");
            response.setDescripcion("exitoso");
            response.setData(savedPerson);
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

            response.setCodigoRespuesta("000");
            response.setDescripcion("exitoso");
            response.setData(address);
            return response;
        }
    }

    // Mapeo para la solicitud PUT en la ruta "/personas/{id}"
    @PutMapping("/personas/{id}")
    public Person updatePersonAndAddress(@PathVariable Integer id, @RequestBody Person updatedPerson) {
        // Este método maneja la solicitud PUT para actualizar una persona

        // Buscar la persona existente en la base de datos utilizando el ID
        // proporcionado
        Optional<Person> existingPersonOptional = personaRepository.findById(id);

        if (existingPersonOptional.isPresent()) {
            // Si la persona existe en la base de datos
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

            // Devuelve la Person actualizada
            return existingPerson;
        } else {
            // Si la persona no existe, devuelve null como manejo de error
            return null;
        }
    }

    // Mapeo para la solicitud DELETE en la ruta "/personas/{id}"
    @DeleteMapping("/personas/{id}")
    public Person deletePrueba(@PathVariable Integer id) {
        // Este método maneja la solicitud DELETE para marcar una persona como inactiva

        // Buscar la persona existente en la base de datos utilizando el ID
        // proporcionado
        Optional<Person> existingPersonOptional = personaRepository.findById(id);

        if (existingPersonOptional.isPresent()) {
            // Si la persona existe en la base de datos
            Person existingPerson = existingPersonOptional.get();

            // Marcar la persona como inactiva
            existingPerson.setActivo(2);

            // Guardar los cambios en la persona en la base de datos
            existingPerson = personaRepository.save(existingPerson);

            // Devolver la instancia de Person actualizada
            return existingPerson;
        } else {
            // Si la persona no existe, devolver null como manejo de error
            return null;
        }
    }

}
