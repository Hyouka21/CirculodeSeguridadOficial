package com.sosa.circulodeseguridadoficial.entidades.dto;

import java.io.Serializable;

public class IdentificadorDto implements Serializable {
    private String Identificador;

    public String getIdentificador() {
        return Identificador;
    }

    public IdentificadorDto(String identificador) {
        Identificador = identificador;
    }

    public void setIdentificador(String identificador) {
        Identificador = identificador;
    }
}
