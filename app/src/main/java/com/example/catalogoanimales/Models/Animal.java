package com.example.catalogoanimales.Models;

import java.io.Serializable;

public class Animal implements Serializable
{
    public int id;
    public String especie;
    public String nombreCientifico;
    public String habitat;
    public int pesoPromedio;
    public String estadoConservacion;
    public String tipo;
    public InformacionAdicional informacionAdicional;

    public Animal(int id, String especie, String nombreCientifico, String habitat, int pesoPromedio, String estadoConservacion, String tipo, InformacionAdicional informacionAdicional)
    {
        this.id = id;
        this.especie = especie;
        this.nombreCientifico = nombreCientifico;
        this.habitat = habitat;
        this.pesoPromedio = pesoPromedio;
        this.estadoConservacion = estadoConservacion;
        this.tipo = tipo;
        this.informacionAdicional = informacionAdicional;
    }

    public Animal() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getNombreCientifico() {
        return nombreCientifico;
    }

    public void setNombreCientifico(String nombreCientifico) {
        this.nombreCientifico = nombreCientifico;
    }

    public String getHabitat() {
        return habitat;
    }

    public void setHabitat(String habitat) {
        this.habitat = habitat;
    }

    public int getPesoPromedio() {
        return pesoPromedio;
    }

    public void setPesoPromedio(int pesoPromedio) {
        this.pesoPromedio = pesoPromedio;
    }

    public String getEstadoConservacion() {
        return estadoConservacion;
    }

    public void setEstadoConservacion(String estadoConservacion) {
        this.estadoConservacion = estadoConservacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public InformacionAdicional getInformacionAdicional() {
        return informacionAdicional;
    }

    public void setInformacionAdicional(InformacionAdicional informacionAdicional) {
        this.informacionAdicional = informacionAdicional;
    }
}
