package com.example.karlaconk.modules;

public class Usuario {
    private int idUsuario;
    private String nombreUsuario;
    private String clave;
    private byte[] imagenUsuario;
    private static boolean sesionIniciada = false;
    private static int idUsuarioActual;


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
    public static boolean isSesionIniciada() {
        return sesionIniciada;
    }

    public static void setSesionIniciada(boolean estado) {
        sesionIniciada = estado;
    }


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

    public static int getIdUsuarioActual() {
        return idUsuarioActual;
    }

    public static void setIdUsuarioActual(int idUsuarioActual) {
        Usuario.idUsuarioActual = idUsuarioActual;
    }
}

