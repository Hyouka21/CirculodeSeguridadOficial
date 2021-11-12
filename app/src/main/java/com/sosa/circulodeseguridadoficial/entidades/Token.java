package com.sosa.circulodeseguridadoficial.entidades;

import java.io.Serializable;

public class Token implements Serializable {
    public String Token ;
    public String Expiracion;

    public Token() {
    }

    public Token(String token, String expiracion) {
        Token = token;
        Expiracion = expiracion;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getExpiracion() {
        return Expiracion;
    }

    public void setExpiracion(String expiracion) {
        Expiracion = expiracion;
    }

    @Override
    public String toString() {
        return "Token{" +
                "Token='" + Token + '\'' +
                ", Expiracion='" + Expiracion + '\'' +
                '}';
    }
}
