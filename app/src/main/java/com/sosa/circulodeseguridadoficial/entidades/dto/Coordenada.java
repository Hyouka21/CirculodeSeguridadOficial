package com.sosa.circulodeseguridadoficial.entidades.dto;

import java.io.Serializable;

public class Coordenada implements Serializable {
    private String coordenadaX;
    private String coordeanadaY;

    public Coordenada() {
    }

    public Coordenada(String coordenadaX, String coordeanadaY) {
        this.coordenadaX = coordenadaX;
        this.coordeanadaY = coordeanadaY;
    }

    public String getCoordenadaX() {
        return coordenadaX;
    }

    public void setCoordenadaX(String coordenadaX) {
        this.coordenadaX = coordenadaX;
    }

    public String getCoordeanadaY() {
        return coordeanadaY;
    }

    public void setCoordeanadaY(String coordeanadaY) {
        this.coordeanadaY = coordeanadaY;
    }
}
