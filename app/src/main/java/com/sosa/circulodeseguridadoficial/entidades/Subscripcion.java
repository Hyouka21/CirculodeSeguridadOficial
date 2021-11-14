package com.sosa.circulodeseguridadoficial.entidades;

import java.io.Serializable;

public class Subscripcion implements Serializable {
    private String email;
    private String nickName;
    private boolean estado;
    private String avatar;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getAvatar() {
        String url = "http://192.169.1.4:45455/"+avatar.replace("\\", "/");
        return url;
    }

    public void setAvatar(String avatar) {
        avatar = avatar;
    }

    @Override
    public String toString() {
        return "Subscripcion{" +
                "email='" + email + '\'' +
                ", nickName='" + nickName + '\'' +
                ", estado=" + estado +
                ", Avatar='" + avatar + '\'' +
                '}';
    }
}
