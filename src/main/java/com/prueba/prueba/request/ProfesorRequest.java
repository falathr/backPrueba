package com.prueba.prueba.request;

import jakarta.validation.constraints.Pattern;

public class ProfesorRequest {
    
    @Pattern(regexp = "^-?[0-9]+$", message = "el postalCode no es valido")
    private Integer salario;

    public ProfesorRequest() {
    }

    public Integer getSalario() {
        return salario;
    }

    public void setSalario(Integer salario) {
        this.salario = salario;
    }

    
}
