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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
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
        cargarListasReproduccionUsuarioActual();

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

    /**
     * metodopara crear una playlist nueva
     * */
    public void crearPlaylist(ActionEvent actionEvent) {
        String nombreLista = nombrePlaylistTextField.getText();

        if (!nombreLista.isEmpty() && !nombreLista.isBlank()) {
            int idUsuario = ObtenerIdUsuarioActual();

            // llamamos al método insertarListaReproduccion para crear la nueva playlist
            GestionBD.insertarListaReproduccion(nombreLista, idUsuario);
            nombrePlaylistTextField.setText("");
            cargarListasReproduccionUsuarioActual();

        } else {
            nombreSinRellenarCrearLabel.setVisible(true);
        }
    }

    /**
     * metodo para eliminar una lista del usuario
     * */
    public void eliminarPlaylist(ActionEvent actionEvent) {
        // obtenemos la playlist seleccionada en la tabla
        Playlist playlistSeleccionada = listasTableView.getSelectionModel().getSelectedItem();

        if (playlistSeleccionada != null) {
            // mostramos un cuadro de diálogo de confirmación
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar eliminación");
            alert.setHeaderText("¿Estás seguro de que deseas eliminar la playlist?");
            alert.setContentText("Esta acción no se puede deshacer.");

            // obtenemos la respuesta del usuario
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                // eliminamos la playlist de la base de datos
                GestionBD.eliminarPlaylist(playlistSeleccionada.getIdLista());

                // actualizamos la tabla de listas de reproducción
                cargarListasReproduccionUsuarioActual();
            }
        } else {
            // Mostrar un mensaje si no se selecciona ninguna playlist
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText("No se ha seleccionado ninguna playlist");
            alert.setContentText("Por favor, selecciona una playlist para eliminar.");
            alert.showAndWait();
        }
    }

    /**
     * metodo para editar el nombre de una playlist
     * */
    public void editarNombrePlaylist() {
        // obtenemos la playlist seleccionada desde la tabla
        Playlist playlistSeleccionada = listasTableView.getSelectionModel().getSelectedItem();

        if (playlistSeleccionada != null) {
            // obtenemos el nuevo nombre desde el campo de texto
            String nuevoNombre = editarNombrePlaylistTextField.getText();

            // verificamos que el nuevo nombre no esté vacío o en blanco
            if (!nuevoNombre.isEmpty() && !nuevoNombre.isBlank()) {
                // actualizamos el nombre de la playlist en la base de datos
                GestionBD.actualizarNombrePlaylist(playlistSeleccionada.getIdLista(), nuevoNombre);

                // actualizamos la lista observable para mostrar el cambio en la interfaz
                playlistSeleccionada.setNombreLista(nuevoNombre);
                listasTableView.refresh();

                // limpiamos el campo de texto después de la edición
                editarNombrePlaylistTextField.clear();

                System.out.println("Nombre de la playlist actualizado correctamente.");
                nombreSinRellenarEditarLabel.setVisible(false);
            } else {
                nombreSinRellenarEditarLabel.setVisible(true);
            }
        }
    }

    /**
     * metodo para devolver el id del usuario actual
     * */
    public static int ObtenerIdUsuarioActual() {
        // devolvemosel id del usuario que ha iniciadosecion en el metodo iniciarSesion()
        return Usuario.getIdUsuarioActual();
    }

    /**
     * metodopara cargar en el tableview las listas del usuario que ha iniciado sesion
     * */
    public void cargarListasReproduccionUsuarioActual() {
        // obtenemos el ID del usuario actual
        int idUsuario = ObtenerIdUsuarioActual();

        // obtenemos las listas de reproducción del usuario actual desde la base de datos
        ObservableList<Playlist> listasReproduccion = GestionBD.obtenerListasReproduccionUsuario(idUsuario);

        // limpiamos y cargamos las listas de reproducción en la tabla
        listasTableView.getItems().clear();
        listasTableView.getItems().addAll(listasReproduccion);
        listaTableColum.setCellValueFactory(new PropertyValueFactory<>("nombreLista"));
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
