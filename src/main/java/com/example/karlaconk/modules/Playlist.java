package com.example.karlaconk.modules;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @Clase: Playlist,para registrar las playlist de el usuario*/
public class Playlist {
    private int idLista;
    private String nombreLista;
    private int idUsuario;
    private List<Cancion> canciones;

    // Constructores, getters y setters

    public Playlist(int idLista, String nombreLista, int idUsuario) {
        this.idLista = idLista;
        this.nombreLista = nombreLista;
        this.idUsuario = idUsuario;
        this.canciones = new ArrayList<>();
    }

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

    public List<Cancion> getCanciones() {
        return canciones;
    }

    public void setCanciones(List<Cancion> canciones) {
        this.canciones = canciones;
    }


}
