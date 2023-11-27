package com.example.karlaconk.modules;

public class Favoritos {
    private int idFavorito;
    private int idUsuario;
    private int idCancion;

    // Constructores, getters y setters

    public Favoritos() {
        // Constructor vacío
    }

    public Favoritos(int idFavorito, int idUsuario, int idCancion) {
        this.idFavorito = idFavorito;
        this.idUsuario = idUsuario;
        this.idCancion = idCancion;
    }

    // Getters y setters para cada campo

    public int getIdFavorito() {
        return idFavorito;
    }

    public void setIdFavorito(int idFavorito) {
        this.idFavorito = idFavorito;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdCancion() {
        return idCancion;
    }

    public void setIdCancion(int idCancion) {
        this.idCancion = idCancion;
    }

}
