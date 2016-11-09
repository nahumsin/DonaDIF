package com.example.nahumsin.donadif;


/**
 * Created by jxsxs on 8/11/16.
 */

public class Familia {
    int id;
    String nombre,direccion,descripcion,imagen;


    public Familia(int id, String nombre, String direccion, String descripcion, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }

    public Familia(String nombre, String direccion, String descripcion, String imagen) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.descripcion = descripcion;
        this.imagen = imagen;
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
}