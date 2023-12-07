package com.example.karlaconk.controllers;

import com.example.karlaconk.modules.*;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlaylistController implements Initializable {

    @FXML
    private Button addSongToPlaylistButton;

    @FXML
    private TableColumn artistaTableColum;

    @FXML
    private TextField buscarTextField;

    @FXML
    private TableView<Cancion> cancionesTableView;

    @FXML
    private Button cerrarVentanaButton;

    @FXML
    private Button crearPlaylistButton;

    @FXML
    private Pane dropPlaylistPane;

    @FXML
    private Button dropSongFromPlailystButton;

    @FXML
    private TableColumn duracionTableColum;

    @FXML
    private TextField editarNombrePlaylistTextField;

    @FXML
    private Button editarPlaylistButton;

    @FXML
    private Pane editarPlaylistPane;

    @FXML
    private Button eliminarPlaylistButton;

    @FXML
    private Button eliminarPlaylistSelecionadaButton;

    @FXML
    private TableColumn generoTableColum;

    @FXML
    private Button guardarNombreEditButton;

    @FXML
    private TableColumn listaTableColum;

    @FXML
    private TableView<Playlist> listasTableView;

    @FXML
    private Pane newPlaylistPane;

    @FXML
    private TextField nombrePlaylistTextField;

    @FXML
    private Label nombreSinRellenarCrearLabel;

    @FXML
    private Label nombreSinRellenarEditarLabel;

    @FXML
    private Button nuevaPlaylistButton;

    @FXML
    private TableColumn releaseDateTableColum;

    @FXML
    private Label seleccionCancionEditLabel;

    @FXML
    private TableColumn tituloTableColum;

    ObservableList<Cancion> cancionObservableList = FXCollections.observableArrayList();
    private PrincipalController principalController;



    public void setPrincipalController(PrincipalController principalController) {
        this.principalController = principalController;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        newPlaylistPane.setVisible(false);
        editarPlaylistPane.setVisible(false);
        dropPlaylistPane.setVisible(false);
        nombreSinRellenarCrearLabel.setVisible(false);
        nombreSinRellenarEditarLabel.setVisible(false);

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

                String queryCancionTitulo = queryOutput.getString("titulo");
                String queryCancionArtista = queryOutput.getString("artista");
                String queryCancionDuracion = queryOutput.getString("duracion");
                String queryCancionGenero = queryOutput.getString("genero");
                String queryCancionReleaseDate = queryOutput.getString("release_date");

                // rellenamos elObservable List de la interfaz principal con las canciones de la base de datos para que sean ovservables
                cancionObservableList.add(new Cancion(queryCancionTitulo, queryCancionArtista, queryCancionDuracion, queryCancionGenero, queryCancionReleaseDate));

            }

            // PropertyValueFactory corresponde a el nuevo campo de Cancion
            // los tableColums son los que establecimos en la clase desde el view
            tituloTableColum.setCellValueFactory(new PropertyValueFactory<>("titulo"));
            artistaTableColum.setCellValueFactory(new PropertyValueFactory<>("artista"));
            duracionTableColum.setCellValueFactory(new PropertyValueFactory<>("duracion"));
            generoTableColum.setCellValueFactory(new PropertyValueFactory<>("genero"));
            releaseDateTableColum.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));


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

    /**
     * metodo para mostrar ñlas opciones CRUD de lasplaylist
     * */
    public void accionBotonesEdicion(ActionEvent actionEvent){
        if (actionEvent.getSource() == nuevaPlaylistButton){
            newPlaylistPane.setVisible(true);
            editarPlaylistPane.setVisible(false);
            dropPlaylistPane.setVisible(false);
        } else if (actionEvent.getSource() == editarPlaylistButton) {
            newPlaylistPane.setVisible(false);
            editarPlaylistPane.setVisible(true);
            dropPlaylistPane.setVisible(false);
        }else if (actionEvent.getSource() == eliminarPlaylistButton) {
            newPlaylistPane.setVisible(false);
            editarPlaylistPane.setVisible(false);
            dropPlaylistPane.setVisible(true);
        }else {
            newPlaylistPane.setVisible(false);
            editarPlaylistPane.setVisible(false);
            dropPlaylistPane.setVisible(false);
        }

    }

    public void crearPlaylist(ActionEvent actionEvent) {
        String nombreLista = nombrePlaylistTextField.getText();

        if (!nombreLista.isEmpty() && !nombreLista.isBlank()) {
            int idUsuario = ObtenerIdUsuarioActual();

            // llamamos al método insertarListaReproduccion para crear la nueva playlist
            GestionBD.insertarListaReproduccion(nombreLista, idUsuario);

        } else {
            nombreSinRellenarCrearLabel.setVisible(true);
        }
    }

    /**
     * metodo para devolver el id del usuario actual
     * */
    private int ObtenerIdUsuarioActual() {
        // devolvemosel id del usuario que ha iniciadosecion en el metodo iniciarSesion()
        return Usuario.getIdUsuarioActual();
    }

    /**
     * metodo para volver a la pantalla principal al pulsar el boton de volver
     * */
    public void volver(ActionEvent actionEvent) {
        cerrarVentanaPlaylist();
        cargarVentanaPrincipal();
    }

    /**
     * metodopara cerrarla ventana de manejo de playlist
     * */
    public void cerrarVentanaPlaylist(){
        Stage stage = (Stage) cerrarVentanaButton.getScene().getWindow();
        stage.close();
    }

    /**
     * metodo para cargar la ventana principal
     * */
    private void cargarVentanaPrincipal() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/karlaconk/principal-view.fxml"));
            Parent root = fxmlLoader.load();
            Stage principalStage = new Stage();
            principalStage.setTitle("Karla Home");
            principalStage.setScene(new Scene(root));

            principalStage.initModality(Modality.APPLICATION_MODAL);

            PrincipalController principalController = fxmlLoader.getController();
            principalController.setPlaylistController(this);
            principalStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
