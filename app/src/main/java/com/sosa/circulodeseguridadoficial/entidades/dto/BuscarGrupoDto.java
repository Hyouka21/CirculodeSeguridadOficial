package com.sosa.circulodeseguridadoficial.entidades.dto;

public class BuscarGrupoDto {
    private String nombre;

    public BuscarGrupoDto(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
