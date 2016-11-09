package com.example.nahumsin.donadif;

/**
 * Created by SistemasNA on 08/11/2016.
 */

class Family {
    int id;
    String nombre;
    String direccion;
    String descripcion;

    public Family(Integer id, String nombre, String direccion, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.descripcion = descripcion;
    }

    public Family() {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public Family(String nombre, String direccion, String descripcion) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Family{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
