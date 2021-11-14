package com.sosa.circulodeseguridadoficial.entidades.dto;

public class EditarSubscripcionDto {
    private String email;
    private String Identificador;
    private boolean Estado;

    public EditarSubscripcionDto(String email, String identificador, boolean estado) {
        this.email = email;
        Identificador = identificador;
        Estado = estado;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdentificador() {
        return Identificador;
    }

    public void setIdentificador(String identificador) {
        Identificador = identificador;
    }

    public boolean isEstado() {
        return Estado;
    }

    public void setEstado(boolean estado) {
        Estado = estado;
    }
}
