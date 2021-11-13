package com.sosa.circulodeseguridadoficial.entidades.dto;

public class CrearLocalizacion {

    public float coordenadaX;
    public float coordenadaY;

    public CrearLocalizacion(float coordenadaX, float coordenadaY) {
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
    }

    public float getCoordenadaX() {
        return coordenadaX;
    }

    public void setCoordenadaX(float coordenadaX) {
        this.coordenadaX = coordenadaX;
    }

    public float getCoordenadaY() {
        return coordenadaY;
    }

    public void setCoordenadaY(float coordenadaY) {
        this.coordenadaY = coordenadaY;
    }
}
