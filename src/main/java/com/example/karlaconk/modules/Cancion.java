package com.example.karlaconk.modules;

import javafx.beans.property.*;


public class Cancion {
//    int idCancion;
//    String titulo;
//    String artista;
//    String duracion;
//    boolean favorito;
//    String genero;
//    String releaseDate;
//    String audioCancion;
//    String imagenCancion;


    // Constructores, getters y setters

//    public Cancion(Integer queryCancionID, String queryCancionTitulo, String queryCancionArtista, String queryCancionDuracion, String queryCancionGenero, String queryCancionReleaseDate) {
//        // Constructor vacío
//    }

    private final IntegerProperty idCancion = new SimpleIntegerProperty();
    private final StringProperty titulo = new SimpleStringProperty();
    private final StringProperty artista = new SimpleStringProperty();
    private final StringProperty duracion = new SimpleStringProperty();
    private final BooleanProperty favorito = new SimpleBooleanProperty();
    private final StringProperty genero = new SimpleStringProperty();
    private final StringProperty releaseDate = new SimpleStringProperty();
    private final StringProperty audioCancion = new SimpleStringProperty();
    private final StringProperty imagenCancion = new SimpleStringProperty();


    public Cancion(Integer queryCancionID, String queryCancionTitulo, String queryCancionArtista, String queryCancionDuracion, Boolean queryCancionFavorito, String queryCancionGenero, String queryCancionReleaseDate, String queryCancionAudio, String queryCancionImagen) {
    }

    public int getIdCancion() {
        return idCancion.get();
    }

    public IntegerProperty idCancionProperty() {
        return idCancion;
    }

    public void setIdCancion(int idCancion) {
        this.idCancion.set(idCancion);
    }

    public String getTitulo() {
        return titulo.get();
    }

    public StringProperty tituloProperty() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo.set(titulo);
    }

    public String getArtista() {
        return artista.get();
    }

    public StringProperty artistaProperty() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista.set(artista);
    }

    public String getDuracion() {
        return duracion.get();
    }

    public StringProperty duracionProperty() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion.set(duracion);
    }

    public boolean isFavorito() {
        return favorito.get();
    }

    public BooleanProperty favoritoProperty() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito.set(favorito);
    }

    public String getGenero() {
        return genero.get();
    }

    public StringProperty generoProperty() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero.set(genero);
    }

    public String getReleaseDate() {
        return releaseDate.get();
    }

    public StringProperty releaseDateProperty() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate.set(releaseDate);
    }

    public String getAudioCancion() {
        return audioCancion.get();
    }

    public StringProperty audioCancionProperty() {
        return audioCancion;
    }

    public void setAudioCancion(String audioCancion) {
        this.audioCancion.set(audioCancion);
    }

    public String getImagenCancion() {
        return imagenCancion.get();
    }

    public StringProperty imagenCancionProperty() {
        return imagenCancion;
    }

    public void setImagenCancion(String imagenCancion) {
        this.imagenCancion.set(imagenCancion);
    }
}