package com.example.karlaconk.modules;

public class HistorialReproduccion {
    private int idHistorial;
    private int idUsuario;
    private int idCancion;

    // Constructores, getters y setters

    public HistorialReproduccion() {
        // Constructor vacío
    }

    public HistorialReproduccion(int idHistorial, int idUsuario, int idCancion) {
        this.idHistorial = idHistorial;
        this.idUsuario = idUsuario;
        this.idCancion = idCancion;
    }

    // Getters y setters para cada campo

    public int getIdHistorial() {
        return idHistorial;
    }

    public void setIdHistorial(int idHistorial) {
        this.idHistorial = idHistorial;
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
