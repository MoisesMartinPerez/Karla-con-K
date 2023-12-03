package com.example.karlaconk.modules;

import java.time.LocalTime;

public class Cancion {
    private int idCancion;
    private String titulo;
    private String artista;
    private LocalTime duracion;
    private boolean favorito;
    private String genero;
    private int anio;
    private byte[] audioCancion;
    private String imagenCancion;

    // Constructores, getters y setters

    public Cancion() {
        // Constructor vacío
    }

    public Cancion(int idCancion, String titulo, String artista, LocalTime duracion,
                   boolean favorito, String genero, int anio, byte[] audioCancion, String imagenCancion) {
        this.idCancion = idCancion;
        this.titulo = titulo;
        this.artista = artista;
        this.duracion = duracion;
        this.favorito = favorito;
        this.genero = genero;
        this.anio = anio;
        this.audioCancion = audioCancion;
        this.imagenCancion = imagenCancion;
    }

    // Getters y setters para cada campo

    public int getIdCancion() {
        return idCancion;
    }

    public void setIdCancion(int idCancion) {
        this.idCancion = idCancion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public LocalTime getDuracion() {
        return duracion;
    }

    public void setDuracion(LocalTime duracion) {
        this.duracion = duracion;
    }

    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public byte[] getAudioCancion() {
        return audioCancion;
    }

    public void setAudioCancion(byte[] audioCancion) {
        this.audioCancion = audioCancion;
    }

    public String getImagenCancion() {
        return imagenCancion;
    }

    public void setImagenCancion(String imagenCancion) {
        this.imagenCancion = imagenCancion;
    }

}