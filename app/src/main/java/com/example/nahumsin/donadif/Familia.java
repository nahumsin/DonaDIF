package com.example.nahumsin.donadif;


/**
 * Created by jxsxs on 8/11/16.
 */

public class Familia {
    String id, nombre,direccion,descripcion,imagen,entregado;


    public Familia(String id, String nombre, String direccion, String descripcion, String imagen, String entregado) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.entregado = entregado;
    }

    public Familia(String nombre, String direccion, String descripcion, String imagen,String entregado) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.entregado = entregado;
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

    public String getId() {
        return id;
    }
    public String getEntregado(){return entregado;}
}