package com.example.karlaconk.modules;

public class Usuario {
    private int idUsuario;
    private String nombreUsuario;
    private String clave;
    private byte[] imagenUsuario;

    // Constructores, getters y setters

    public Usuario() {
        // Constructor vacío
    }

    public Usuario(int idUsuario, String nombreUsuario, String clave, byte[] imagenUsuario) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.clave = clave;
        this.imagenUsuario = imagenUsuario;
    }

    // Getters y setters para cada campo

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public byte[] getImagenUsuario() {
        return imagenUsuario;
    }

    public void setImagenUsuario(byte[] imagenUsuario) {
        this.imagenUsuario = imagenUsuario;
    }

}
