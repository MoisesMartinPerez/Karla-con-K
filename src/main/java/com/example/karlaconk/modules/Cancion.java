package com.example.karlaconk.modules;

import javafx.beans.property.*;


public class Cancion {
    int idCancion;
    String titulo;
    String artista;
    String duracion;
    boolean favorito;
    String genero;
    String releaseDate;
    String audioCancion;
    String imagenCancion;


    // Constructores, getters y setters


    public Cancion(int idCancion, String titulo, String artista, String duracion, boolean favorito, String genero, String releaseDate, String audioCancion, String imagenCancion) {
        this.idCancion = idCancion;
        this.titulo = titulo;
        this.artista = artista;
        this.duracion = duracion;
        this.favorito = favorito;
        this.genero = genero;
        this.releaseDate = releaseDate;
        this.audioCancion = audioCancion;
        this.imagenCancion = imagenCancion;
    }

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

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
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

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getAudioCancion() {
        return audioCancion;
    }

    public void setAudioCancion(String audioCancion) {
        this.audioCancion = audioCancion;
    }

    public String getImagenCancion() {
        return imagenCancion;
    }

    public void setImagenCancion(String imagenCancion) {
        this.imagenCancion = imagenCancion;
    }
}