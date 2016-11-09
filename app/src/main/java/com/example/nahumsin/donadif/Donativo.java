package com.example.nahumsin.donadif;

/**
 * Created by nahumsin on 08/11/16.
 */

public class Donativo {

    public String idDonativo;
    public String idFamila;
    public String idDonador;
    public int entregado;


    public Donativo(String idDonativo,String idFamilia,String idDonador,int entregado){
        this.idDonador = idDonador;
        this.idFamila = idFamilia;
        this.idDonativo = idDonativo;
        this.entregado = entregado;
    }
    public String getIdDonativo() {
        return idDonativo;
    }

    public void setIdDonativo(String idDonativo) {
        this.idDonativo = idDonativo;
    }

    public String getIdFamila() {
        return idFamila;
    }

    public void setIdFamila(String idFamila) {
        this.idFamila = idFamila;
    }

    public String getIdDonador() {
        return idDonador;
    }

    public void setIdDonador(String idDonador) {
        this.idDonador = idDonador;
    }
    public int getEntregado() {
        return entregado;
    }

    public void setEntregado(int entregado) {
        this.entregado = entregado;
    }
}
