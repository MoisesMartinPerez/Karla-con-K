package com.example.karlaconk.controllers;

import com.example.karlaconk.modules.Cancion;
import com.example.karlaconk.modules.Conexion;
import com.example.karlaconk.modules.Playlist;
import com.example.karlaconk.modules.Usuario;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
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
    private ImageView imagenusuarioImageView;

    @FXML
    private TableColumn listaTableColum;

    @FXML
    private TableView<Playlist> listasTableView;

    @FXML
    private Label nombreUsuarioLabel;

    @FXML
    private Button perfilButton;

    @FXML
    private Button playButton;

    @FXML
    private ImageView playImageView;

    @FXML
    private Button playlistButton;

    @FXML
    private TableColumn releaseDateTableColum;

    @FXML
    private Label remainingTiempoCancionLabel;

    @FXML
    private Label tituloCancionReprodLabel;

    @FXML
    private TableColumn tituloTableColum;

    ObservableList<Cancion> cancionObservableList = FXCollections.observableArrayList();

    private Usuario usuario;

    private RegistrarseController registrarseController;
    private InicioSesionController inicioSesionController;
    private CambiosPerfilController cambiosPerfilController;
    private PlaylistController playlistController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tablaInicial();
        if(Usuario.isSesionIniciada()){
            botonRegistrar.setVisible(false);
            botonInicioSesion.setVisible(false);
            // Si hay un usuario, muestra su imagen y nombre
            if (usuario != null) {
                System.out.println("Longitud de la imagen: " + usuario.getImagenUsuario().length);
                imagenusuarioImageView.setImage(new Image(new ByteArrayInputStream(usuario.getImagenUsuario())));
                nombreUsuarioLabel.setText(usuario.getNombreUsuario());
            } else {
                System.out.println("¡Advertencia! El usuario es nulo.");
            }
        } else {
            nombreUsuarioLabel.setVisible(false);
            imagenusuarioImageView.setVisible(false);
            perfilButton.setVisible(false);
        }
    }

    public void setRegistrarseController(RegistrarseController registrarseController) {
        this.registrarseController = registrarseController;
    }
    public void setInicioSesionController(InicioSesionController inicioSesionController){
        this.inicioSesionController = inicioSesionController;
    }
    public void setCambiosPerfilController(CambiosPerfilController cambiosPerfilController) {
        this.cambiosPerfilController = cambiosPerfilController;
    }
    public void setPlaylistController(PlaylistController playlistController){
        this.playlistController = playlistController;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        System.out.println("Usuario establecido en PrincipalController: " + usuario);
        actualizarInterfazGrafica();
    }

    public Usuario getUsuario() {
        return usuario != null ? usuario: new Usuario(); // Si usuario es null, devuelve un nuevo Usuario
    }

    private void actualizarInterfazGrafica() {
        // Implementa la actualización de la interfaz gráfica aquí según los cambios en el usuario
        // Por ejemplo, actualiza la imagen y el nombre de usuario en la GUI
        if (Usuario.isSesionIniciada()) {
            imagenusuarioImageView.setImage(new Image(new ByteArrayInputStream(usuario.getImagenUsuario())));
            nombreUsuarioLabel.setText(usuario.getNombreUsuario());
        }
    }

    public void modificarPerfil(ActionEvent actionEvent) {
        //Cerrar ventana Principal
        Stage stage = (Stage) perfilButton.getScene().getWindow();
        stage.close();
        System.out.println("ventana principal cerrada");

        cargarVentanaPerfil();

    }

    public void cargarVentanaPerfil(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/karlaconk/perfil-view.fxml"));
            Parent root = loader.load();
            System.out.println("carga fxml gucci");
            Stage perfilStage = new Stage();
            perfilStage.setTitle("Perfil");
            perfilStage.setScene(new Scene(root));

            perfilStage.initModality(Modality.APPLICATION_MODAL);

            CambiosPerfilController cambiosPerfilController = loader.getController();
            cambiosPerfilController.setPrincipalController(this);
            System.out.println("el controller carga guapo");
            perfilStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }

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

    public void registrar(ActionEvent actionEvent) {
        //Cerrar ventana Principal
        Stage stage = (Stage) botonRegistrar.getScene().getWindow();
        stage.close();
        System.out.println("ventana principal cerrada");

        cargarVentanaRegistro();

    }

    public void iniciarSesion(ActionEvent actionEvent) {
        // Cerrar ventana principal
        Stage stage = (Stage) botonInicioSesion.getScene().getWindow();
        stage.close();

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

    @FXML
    private void cargarVentanaPlaylist() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/karlaconk/playlist-view.fxml"));
            Parent root = fxmlLoader.load();
            Stage playlistStage = new Stage();
            playlistStage.setTitle("Mis Playlist");
            playlistStage.setScene(new Scene(root));

            playlistStage.initModality(Modality.APPLICATION_MODAL);

            PlaylistController playlistController = fxmlLoader.getController();
            playlistController.setPrincipalController(this);
            playlistStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

