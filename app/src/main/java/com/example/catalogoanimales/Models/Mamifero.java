package com.example.catalogoanimales.Models;

public class Mamifero extends Animal
{
    public Double temperaturaCorporal;
    public int tiempoGestacion;
    public String alimentacion;

    public Mamifero(int id, String especie, String nombreCientifico, String habitat, int pesoPromedio, String estadoConservacion, String tipo, InformacionAdicional informacionAdicional, Double temperaturaCorporal, int tiempoGestacion, String alimentacion)
    {
        super(id, especie, nombreCientifico, habitat, pesoPromedio, estadoConservacion, tipo, informacionAdicional);
        this.temperaturaCorporal = temperaturaCorporal;
        this.tiempoGestacion = tiempoGestacion;
        this.alimentacion = alimentacion;
    }

    public Double getTemperaturaCorporal() {
        return temperaturaCorporal;
    }

    public void setTemperaturaCorporal(Double temperaturaCorporal) {
        this.temperaturaCorporal = temperaturaCorporal;
    }

    public int getTiempoGestacion() {
        return tiempoGestacion;
    }

    public void setTiempoGestacion(int tiempoGestacion) {
        this.tiempoGestacion = tiempoGestacion;
    }

    public String getAlimentacion() {
        return alimentacion;
    }

    public void setAlimentacion(String alimentacion) {
        this.alimentacion = alimentacion;
    }
}
