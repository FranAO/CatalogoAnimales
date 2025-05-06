package com.example.catalogoanimales.Models;

public class Ave extends Animal {
    public int envergaduraAlas;
    public String colorPlumaje;
    public String tipoPico;

    public Ave(int id, String especie, String nombreCientifico, String habitat, int pesoPromedio, String estadoConservacion, String tipo, InformacionAdicional informacionAdicional, int envergaduraAlas, String colorPlumaje, String tipoPico)
    {
        super(id, especie, nombreCientifico, habitat, pesoPromedio, estadoConservacion, tipo, informacionAdicional);
        this.envergaduraAlas = envergaduraAlas;
        this.colorPlumaje = colorPlumaje;
        this.tipoPico = tipoPico;
    }

    public int getEnvergaduraAlas() {
        return envergaduraAlas;
    }

    public void setEnvergaduraAlas(int envergaduraAlas) {
        this.envergaduraAlas = envergaduraAlas;
    }

    public String getColorPlumaje() {
        return colorPlumaje;
    }

    public void setColorPlumaje(String colorPlumaje) {
        this.colorPlumaje = colorPlumaje;
    }

    public String getTipoPico() {
        return tipoPico;
    }

    public void setTipoPico(String tipoPico) {
        this.tipoPico = tipoPico;
    }
}
