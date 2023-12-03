package com.example.karlaconk.controllers;

import com.example.karlaconk.modules.Cancion;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CancionController {

    @FXML
    private Label artistaLabel;

    @FXML
    private ImageView cancionImageView;

    @FXML
    private Label tituloLabel;

    public void setDatos(Cancion cancion){
        Image imagen = new Image(getClass().getResourceAsStream(cancion.getImagenCancion()));
        cancionImageView.setImage(imagen);
        tituloLabel.setText(cancion.getTitulo());
        artistaLabel.setText(cancion.getArtista());

    }

}
