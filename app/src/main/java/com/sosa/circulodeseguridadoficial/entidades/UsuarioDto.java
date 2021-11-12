package com.sosa.circulodeseguridadoficial.entidades;

import java.io.Serializable;

public class UsuarioDto  {
    private String nickName ;
    private String email ;
    private String avatar ;
    private String fechaNacimiento;

    public UsuarioDto() {
    }

    public UsuarioDto(String nickName, String email, String avatar, String fechaNacimiento) {
        nickName = nickName;
        email = email;
        avatar = avatar;
        fechaNacimiento = fechaNacimiento;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        email = email;
    }



    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        avatar = avatar;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        fechaNacimiento = fechaNacimiento;
    }
}

