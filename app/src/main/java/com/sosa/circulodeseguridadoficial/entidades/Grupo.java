package com.sosa.circulodeseguridadoficial.entidades;

import android.util.Log;

import java.io.Serializable;

public class Grupo implements Serializable {
    private String identificador;
    private String nombre;
    private String descripcion;
    private String avatarGrupo;
    private String fechaCreacion;

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        identificador = identificador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        descripcion = descripcion;
    }

    public String getAvatarGrupo() {
        String url = "http://192.169.1.4:45455/"+avatarGrupo.replace("\\", "/");
        Log.d("pas13",url);
        return url;
    }

    public void setAvatarGrupo(String avatarGrupo) {
        avatarGrupo = avatarGrupo;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        fechaCreacion = fechaCreacion;
    }
}
