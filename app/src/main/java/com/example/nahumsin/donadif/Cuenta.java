package com.example.nahumsin.donadif;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by nahumsin on 07/11/16.
 */

public class Cuenta {

    private String nombreUsuario;
    private String contrasena;
    private String correo;
    private int admin;
    private String idCuenta;

    public Cuenta(String nombreUsuario,String correo,String contrasena,int admin){
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.correo = correo;
        this.admin= admin;
    }

    public void setNombreUsuario(String nombre){
        nombreUsuario = nombre;
    }
    public void setContrasena(String contrasena){
        this.contrasena = contrasena;
    }
    public void setCorreo(String correo){
        this.correo = correo;
    }
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getCorreo() {
        return correo;
    }

    public int getPrivilegio(){
        return admin;
    }
}
