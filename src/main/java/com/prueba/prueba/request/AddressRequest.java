package com.prueba.prueba.request;

import java.io.Serializable;

import jakarta.validation.constraints.Pattern;



public class AddressRequest implements Serializable{

    private String street;
    @Pattern(regexp = "^[a-zA-Z]*$", message = "El campo city debe contener solo letras")
    private String city;
    @Pattern(regexp = "^[a-zA-Z]*$", message = "El campo state debe contener solo letras")
    private String state;
    @Pattern(regexp = "^-?[0-9]+$", message = "el postalCode no es valido")
    private String postalCode;
    @Pattern(regexp = "^[a-zA-Z]*$", message = "El campo country debe contener solo letras")
    private String country;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public AddressRequest() {
    }

}
