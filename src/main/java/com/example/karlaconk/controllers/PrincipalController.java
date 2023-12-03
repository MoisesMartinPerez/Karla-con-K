package com.example.karlaconk.controllers;

import com.example.karlaconk.modules.Cancion;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PrincipalController implements Initializable {

    @FXML
    private Label autorCancionReprodLabel;

    @FXML
    private ImageView añadirplaylistImg;

    @FXML
    private Button backButton;

    @FXML
    private ImageView backButtonImageView;

    @FXML
    private TextField buscarTextField;

    @FXML
    private ImageView cancionReproduciendoImageView;

    @FXML
    private Label currentTiempoCancionLabel;

    @FXML
    private Slider duracionCancionSlider;

    @FXML
    private Button editarPerfilButton;

    @FXML
    private Button favoritosButton;

    @FXML
    private HBox favoritosContainer;

    @FXML
    private ImageView favoritosCorazonImageView;

    @FXML
    private ScrollPane favoritosScrollPane;

    @FXML
    private Button forwardButton;

    @FXML
    private ImageView forwardButtonImageView;

    @FXML
    private ImageView imagenusuarioImageView;

    @FXML
    private Label nombreUsuarioLabel;

    @FXML
    private HBox nuevaPlaylistHBox;

    @FXML
    private Label nuevaPlaylistLabel;

    @FXML
    private Button playButton;

    @FXML
    private ImageView playImageView;

    @FXML
    private Label remainingTiempoCancionLabel;

    @FXML
    private Label tituloCancionReprodLabel;

    @FXML
    private HBox ultimosLanzamientosContainer;

    @FXML
    private ScrollPane ultimosLanzamientosScrollPane;

    List<Cancion> nuevosLanzamientos;
    List<Cancion> favoritos;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nuevosLanzamientos = new ArrayList<>(getNuevosLanzamientos());
        favoritos = new ArrayList<>(getListaFavoritos());

//        try {
//            for (Cancion nuevosLanzamiento : nuevosLanzamientos) {
//                FXMLLoader fxmlLoader = new FXMLLoader();
//                fxmlLoader.setLocation(getClass().getResource("cancion-view.fxml"));
//
//                VBox vBox = fxmlLoader.load();
//                CancionController cancionController = fxmlLoader.getController();
//                cancionController.setDatos(nuevosLanzamiento);
//
//                ultimosLanzamientosContainer.getChildren().add(vBox);
//            }
//
//            for (Cancion favoritosCancion : favoritos) {
//                FXMLLoader fxmlLoader = new FXMLLoader();
//                fxmlLoader.setLocation(getClass().getResource("cancion-view.fxml"));
//
//                VBox vBox = fxmlLoader.load();
//                CancionController cancionController = fxmlLoader.getController();
//                cancionController.setDatos(favoritosCancion);
//
//                favoritosContainer.getChildren().add(vBox);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


    /**
     * metodo para inicializar la lista de canciones predeterminada en lainterfaz
     * */
    private List<Cancion> getNuevosLanzamientos(){
        List<Cancion> ls = new ArrayList<>();

        Cancion cancion = new Cancion();
        cancion.setArtista("La Moises");
        cancion.setTitulo("Punto 40");
        cancion.setImagenCancion("/music_img/punto40.JPG");
        ls.add(cancion);

        cancion = new Cancion();
        cancion.setArtista("La MAngie");
        cancion.setTitulo("Tumbala");
        cancion.setImagenCancion("/music_img/el efecto.JPG");
        ls.add(cancion);

        return ls;
    }

    private List<Cancion> getListaFavoritos() {
        List<Cancion> ls = new ArrayList<>();

        Cancion cancion = new Cancion();
        cancion.setArtista("La Moises");
        cancion.setTitulo("Punto 40");
        cancion.setImagenCancion("/music_img/punto40.JPG");
        ls.add(cancion);

        return ls;
    }
}

