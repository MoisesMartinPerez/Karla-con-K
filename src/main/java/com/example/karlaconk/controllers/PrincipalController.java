package com.example.karlaconk.controllers;

import com.example.karlaconk.modules.Cancion;
import com.example.karlaconk.modules.Conexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PrincipalController implements Initializable{

    @FXML
    private TableColumn artistaTableColum;

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
    private TableView<Cancion> cancionesTableView;

    @FXML
    private Label currentTiempoCancionLabel;

    @FXML
    private Slider duracionCancionSlider;

    @FXML
    private TableColumn duracionTableColum;

    @FXML
    private Button favoritosButton;

    @FXML
    private ImageView favoritosCorazonImageView;

    @FXML
    private TableColumn generoTableColum;

    @FXML
    private TableColumn idTableColum;

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
    private TableColumn portadaTableColum;

    @FXML
    private TableColumn releaseDateTableColum;

    @FXML
    private TableColumn favoritoTableColum;

    @FXML
    private TableColumn audioTableColum;

    @FXML
    private Label remainingTiempoCancionLabel;

    @FXML
    private Label tituloCancionReprodLabel;

    @FXML
    private TableColumn tituloTableColum;

    ObservableList<Cancion> cancionObservableList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tablaInicial();
    }

    public void tablaInicial(){
        Connection connectDB= Conexion.getConnection();

        // sentencia SQL - query para ejecutr la instruccion
        String tablaCancionQuery = "SELECT id_cancion, titulo, artista, duracion, favorito, genero, release_date, audio_cancion, imagen_cancion FROM canciones";

        try{

            PreparedStatement statement = connectDB.prepareStatement(tablaCancionQuery);
            ResultSet queryOutput = statement.executeQuery(tablaCancionQuery);

            while(queryOutput.next()){

                Integer queryCancionID = queryOutput.getInt("id_cancion");
                String queryCancionTitulo = queryOutput.getString("titulo");
                String queryCancionArtista = queryOutput.getString("artista");
                String queryCancionDuracion = queryOutput.getString("duracion");
                Boolean queryCancionFavorito = queryOutput.getBoolean("favorito");
                String queryCancionGenero = queryOutput.getString("genero");
                String queryCancionReleaseDate = queryOutput.getString("release_date");
                String queryCancionAudio = queryOutput.getString("audio_cancion");
                String queryCancionImagen = queryOutput.getString("imagen_cancion");

                // rellenamos elObservable List de la interfaz principal con las canciones de la base de datos para que sean ovservables
                cancionObservableList.add(new Cancion(queryCancionID, queryCancionTitulo, queryCancionArtista, queryCancionDuracion, queryCancionFavorito, queryCancionGenero, queryCancionReleaseDate, queryCancionAudio, queryCancionImagen));

            }

            // PropertyValueFactory corresponde a el nuevo campo de Cancion
           // los tableColums son los que establecimos en la clase desde el view
            idTableColum.setCellValueFactory(new PropertyValueFactory<>("idCancion"));
            tituloTableColum.setCellValueFactory(new PropertyValueFactory<>("titulo"));
            artistaTableColum.setCellValueFactory(new PropertyValueFactory<>("artista"));
            duracionTableColum.setCellValueFactory(new PropertyValueFactory<>("duracion"));
            favoritoTableColum.setCellValueFactory(new PropertyValueFactory<>("favorito"));
            generoTableColum.setCellValueFactory(new PropertyValueFactory<>("genero"));
            releaseDateTableColum.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
            audioTableColum.setCellValueFactory(new PropertyValueFactory<>("audioCancion"));
            portadaTableColum.setCellValueFactory(new PropertyValueFactory<>("imagenCancion"));


            // hacemos un set a los elementos del TableView de las canciones con la lista Observable rellenada
            cancionesTableView.setItems(cancionObservableList);

            // implementamos un buscador
            FilteredList<Cancion> filteredList = new FilteredList<>(cancionObservableList, b -> true);
            buscarTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate(Cancion -> {

                    // si no hay ningun texto en el buscador se muestran todas las canciones disponibles
                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null){
                        return true;
                    }

                    String buscarPalabra = newValue.toLowerCase();

                    if (Cancion.getTitulo().toLowerCase().indexOf(buscarPalabra) > -1){
                        return true; // buscamos por titulo
                    } else if (Cancion.getArtista().toLowerCase().indexOf(buscarPalabra) > -1) {
                        return true; // buscamos por artista
                    }else if (Cancion.getGenero().toLowerCase().indexOf(buscarPalabra) > -1) {
                        return true; // buscamos por genero
                    }else {
                        return false; // no encuentra ningun mach
                    }
                });
            });

            // creamoun sorted List para las palabras buscadas en procesode escritura
            SortedList<Cancion> sortedList = new SortedList<>(filteredList);

            sortedList.comparatorProperty().bind(cancionesTableView.comparatorProperty());

            // aplicamos la busqueda a la tabla
            cancionesTableView.setItems(sortedList);

        }catch (SQLException e){
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE,null,e);
            e.printStackTrace();
        }
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


}

