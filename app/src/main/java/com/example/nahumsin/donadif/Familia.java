package com.example.nahumsin.donadif;


/**
 * Created by jxsxs on 8/11/16.
 */

public class Familia {
    int id;
    String nombre,direccion,descripcion,imagen;
    int donativos_recividos;

    public Familia(int id, String nombre, String direccion, String descripcion, String imagen, int donativos_recividos) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.donativos_recividos = donativos_recividos;
    }

    public Familia(String nombre, String direccion, String descripcion, String imagen, int donativos_recividos) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.donativos_recividos = donativos_recividos;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public int getId() {
        return id;
    }

    public int getDonativos_recividos() {
        return donativos_recividos;
    }
}