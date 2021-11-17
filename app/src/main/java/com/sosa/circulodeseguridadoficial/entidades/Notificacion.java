package com.sosa.circulodeseguridadoficial.entidades;

import java.util.Date;

public class Notificacion {
    private String titulo;
    private String mensaje;
    private String fechaCreacion;
    private String nombreUsuario;
    private String avatar;
    private boolean estado;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getAvatar() {
        String url = "http://192.169.1.4:45455/"+avatar.replace("\\", "/");

        return url;

    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
