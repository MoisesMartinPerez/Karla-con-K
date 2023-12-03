package com.example.karlaconk.controllers;

import com.example.karlaconk.modules.Cancion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
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
    private Button botonInicioSesion;

    @FXML
    private Button botonRegistrar;

    @FXML
    private TextField buscarTextField;

    @FXML
    private ImageView cancionReproduciendoImageView;

    @FXML
    private Label currentTiempoCancionLabel;

    @FXML
    private Slider duracionCancionSlider;

    @FXML
    private Button favoritosButton;

    @FXML
    private HBox favoritosContainer;

    @FXML
    private ImageView favoritosCorazonImageView;

    @FXML
    private ScrollPane favoritosScrollPane;

    @FXML
    private ImageView imagenusuarioImageView;

    @FXML
    private Button newPlaylistButton;

    @FXML
    private Label nombreUsuarioLabel;

    @FXML
    private Button perfilButton;

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

    public void registrar(ActionEvent actionEvent) {
        cargarVentanaRegistro();
    }

    public void iniciarSesion(ActionEvent actionEvent) {
        cargarVentanaInicioSesion();
    }

    private void cargarVentanaRegistro() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/karlaconk/registrarse-view.fxml"));
            Parent root = fxmlLoader.load();
            Stage registrarseStage = new Stage();
            registrarseStage.setTitle("Registro de Usuario");
            registrarseStage.setScene(new Scene(root));

            registrarseStage.initModality(Modality.APPLICATION_MODAL);

            RegistrarseController registrarseController = fxmlLoader.getController();
            registrarseController.setPrincipalController(this);
            registrarseStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarVentanaInicioSesion(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/karlaconk/iniciarSesion-view.fxml"));
            Parent root = fxmlLoader.load();
            Stage inicioStage = new Stage();
            inicioStage.setTitle("Inicio de Sesión");
            inicioStage.setScene(new Scene(root));

            inicioStage.initModality(Modality.APPLICATION_MODAL);

            InicioSesionController inicioSesionController = fxmlLoader.getController();
            inicioSesionController.setPrincipalController(this);
            inicioStage.showAndWait();
        }catch (IOException e){
            e.printStackTrace();
        }
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

