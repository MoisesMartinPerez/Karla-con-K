package com.example.karlaconk.modules;

import java.util.ArrayList;
import java.util.List;

public class ListaCanciones {
    private int idLista;
    private int idCancion;
    private List<Cancion> canciones; // Lista que almacena las canciones de la lista

    // Constructor, getters y setters

    public ListaCanciones() {
        canciones = new ArrayList<>();
    }

    public ListaCanciones(int idLista, int idCancion) {
        this.idLista = idLista;
        this.idCancion = idCancion;
        this.canciones = new ArrayList<>();
    }

    // Getters y setters para cada campo

    public int getIdLista() {
        return idLista;
    }

    public void setIdLista(int idLista) {
        this.idLista = idLista;
    }

    public int getIdCancion() {
        return idCancion;
    }

    public void setIdCancion(int idCancion) {
        this.idCancion = idCancion;
    }

    public List<Cancion> getCanciones() {
        return canciones;
    }

    public void setCanciones(List<Cancion> canciones) {
        this.canciones = canciones;
    }

}
