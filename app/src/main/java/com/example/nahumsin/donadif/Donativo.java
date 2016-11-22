package com.example.nahumsin.donadif;

/**
 * Created by nahumsin on 08/11/16.
 */

public class Donativo {

    public int idDonativo;
    public int idFamila;
    public int idDonador;
    public int entregado;

    public Donativo(int idDonativo, int idFamila, int idDonador, int entregado) {
        this.idDonativo = idDonativo;
        this.idFamila = idFamila;
        this.idDonador = idDonador;
        this.entregado = entregado;
    }

    public Donativo(int idFamila, int idDonador) {
        this.idFamila = idFamila;
        this.idDonador = idDonador;
    }
    public Donativo(int idDonativo, int idFamila, int idDonador) {
        this.idFamila = idFamila;
        this.idDonador = idDonador;
        this.idDonativo = idDonativo;
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

}
