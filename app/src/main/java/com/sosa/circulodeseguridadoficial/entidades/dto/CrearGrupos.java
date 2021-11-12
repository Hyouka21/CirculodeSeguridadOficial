package com.sosa.circulodeseguridadoficial.entidades.dto;

public class CrearGrupos {
    public String Nombre;
    public String Descripcion;
    public String AvatarGrupo;

    public CrearGrupos() {
    }

    public CrearGrupos(String nombre, String descripcion, String avatarGrupo) {
        Nombre = nombre;
        Descripcion = descripcion;
        AvatarGrupo = avatarGrupo;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getAvatarGrupo() {
        return AvatarGrupo;
    }

    public void setAvatarGrupo(String avatarGrupo) {
        AvatarGrupo = avatarGrupo;
    }
}
