package com.example.karlaconk.modules;

import java.util.List;

public class ListaReproduccion {
    private int idLista;
    private String nombreLista;
    private int idUsuario;

    // Constructores, getters y setters

    public ListaReproduccion() {
        // Constructor vacío
    }

    public ListaReproduccion(int idLista, String nombreLista, int idUsuario) {
        this.idLista = idLista;
        this.nombreLista = nombreLista;
        this.idUsuario = idUsuario;
    }

    // Getters y setters para cada campo

    public int getIdLista() {
        return idLista;
    }

    public void setIdLista(int idLista) {
        this.idLista = idLista;
    }

    public String getNombreLista() {
        return nombreLista;
    }

    public void setNombreLista(String nombreLista) {
        this.nombreLista = nombreLista;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

}