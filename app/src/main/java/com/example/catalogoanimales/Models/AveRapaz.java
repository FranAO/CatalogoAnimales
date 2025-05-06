package com.example.catalogoanimales.Models;

public class AveRapaz extends Ave
{
    public int velocidadVuelo;
    public String tipoPresa;

    public AveRapaz(int id, String especie, String nombreCientifico, String habitat, int pesoPromedio, String estadoConservacion, String tipo, InformacionAdicional informacionAdicional, int envergaduraAlas, String colorPlumaje, String tipoPico, int velocidadVuelo, String tipoPresa)
    {
        super(id, especie, nombreCientifico, habitat, pesoPromedio, estadoConservacion, tipo, informacionAdicional, envergaduraAlas, colorPlumaje, tipoPico);
        this.velocidadVuelo = velocidadVuelo;
        this.tipoPresa = tipoPresa;
    }

    public int getVelocidadVuelo() {
        return velocidadVuelo;
    }

    public void setVelocidadVuelo(int velocidadVuelo) {
        this.velocidadVuelo = velocidadVuelo;
    }

    public String getTipoPresa() {
        return tipoPresa;
    }

    public void setTipoPresa(String tipoPresa) {
        this.tipoPresa = tipoPresa;
    }
}
