package com.example.nahumsin.donadif;

/**
 * Created by nahumsin on 08/11/16.
 */

public class Donativo {

    public int idDonativo;
    public int idFamila;
    public int idDonador;
    public int entregado;


    public Donativo(int idFamila, int idDonador, int entregado) {
        this.idFamila = idFamila;
        this.idDonador = idDonador;
        this.entregado = entregado;
    }

    public int getIdDonativo() {
        return idDonativo;
    }

    public void setIdDonativo(int idDonativo) {
        this.idDonativo = idDonativo;
    }

    public int getIdFamila() {
        return idFamila;
    }

    public void setIdFamila(int idFamila) {
        this.idFamila = idFamila;
    }

    public int getIdDonador() {
        return idDonador;
    }

    public void setIdDonador(int idDonador) {
        this.idDonador = idDonador;
    }

    public int getEntregado() {
        return entregado;
    }

    public void setEntregado(int entregado) {
        this.entregado = entregado;
    }
}
