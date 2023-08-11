package com.prueba.prueba.request;

public class EstudianteRequest {
    private Integer codigoEstudiante;
    private Double promedio;

    public EstudianteRequest() {
    }

    public Integer getCodigoEstudiante() {
        return codigoEstudiante;
    }

    public void setCodigoEstudiante(Integer codigoEstudiante) {
        this.codigoEstudiante = codigoEstudiante;
    }

    public Double getPromedio() {
        return promedio;
    }

    public void setPromedio(Double promedio) {
        this.promedio = promedio;
    }

}
