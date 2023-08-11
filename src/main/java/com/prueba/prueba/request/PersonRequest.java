package com.prueba.prueba.request;

public class PersonRequest {

    private String name;
    private Integer phoneNumber;
    private String email;
    private Integer tipoRoll;
    private AddressRequest address;
    private ProfesorRequest profesor;
    private EstudianteRequest estudiante;
    private Integer activo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AddressRequest getAddress() {
        return address;
    }

    public void setAddress(AddressRequest address) {
        this.address = address;
    }

    public ProfesorRequest getProfesor() {
        return profesor;
    }

    public void setProfesor(ProfesorRequest profesor) {
        this.profesor = profesor;
    }

    public EstudianteRequest getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(EstudianteRequest estudiante) {
        this.estudiante = estudiante;
    }

    public Integer getTipoRoll() {
        return tipoRoll;
    }

    public void setTipoRoll(Integer tipoRoll) {
        this.tipoRoll = tipoRoll;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }

    public PersonRequest() {
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
