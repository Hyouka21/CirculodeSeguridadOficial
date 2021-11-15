package com.sosa.circulodeseguridadoficial.entidades.dto;

public class CrearEventoDto {
    private String identificador;
    private float coordenadaX;
    private float coordenadaY;
    private String nombre;
    private String descripcion;
    private String fechaFinalizacion;

    public CrearEventoDto() {
    }

    public CrearEventoDto(String identificador, float cordenadaX, float coordenadaY, String nombre, String descripcion, String fechaFinalizacion) {
        this.identificador = identificador;
        this.coordenadaX = cordenadaX;
        this.coordenadaY = coordenadaY;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaFinalizacion = fechaFinalizacion;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public float getCordenadaX() {
        return coordenadaX;
    }

    public void setCordenadaX(float cordenadaX) {
        this.coordenadaX = cordenadaX;
    }

    public float getCoordenadaY() {
        return coordenadaY;
    }

    public void setCoordenadaY(float coordenadaY) {
        this.coordenadaY = coordenadaY;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public void setFechaFinalizacion(String fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }
}
