package com.example.karlaconk.controllers;

import com.example.karlaconk.modules.GestionBD;
import com.example.karlaconk.modules.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegistrarseController implements Initializable {
    @FXML
    public Button botonCrear;
    @FXML
    public Button botonCancelar;
    @FXML
    public PasswordField textConfPassw;
    @FXML
    public PasswordField textPassword;
    @FXML
    public TextField textNombre;
    @FXML
    public ImageView imgUserEmpty;
    @FXML
    public Label labelErrorPassword;
    @FXML
    public Label labelErrorUsuarioExist;
    @FXML
    public Label labelErrorCampos;
    @FXML
    public Button buttonImg;


    private File imagenFile;
    private PrincipalController principalController;
    private InicioSesionController inicioSesionController;

    public void setPrincipalController(PrincipalController principalController) {
        this.principalController = principalController;
    }
    public void setInicioSesionController(InicioSesionController inicioSesionController){
        this.inicioSesionController = inicioSesionController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        labelErrorPassword.setVisible(false);
        labelErrorUsuarioExist.setVisible(false);
        labelErrorCampos.setVisible(false);
    }

    public void crearCuenta(ActionEvent actionEvent) {
        if(comprobaciones()){
            String nombre = textNombre.getText();
            String password = textPassword.getText();
            String confPassw = textConfPassw.getText();

            // Llamar al método de inserción en la base de datos
            GestionBD.insertarUsuario(nombre, password, imagenFile);

            // Actualizar el estado de sesión
            Usuario.setSesionIniciada(true);

            System.out.println("usuario insertado");
            System.out.println(Usuario.isSesionIniciada());

            // Cerrar la ventana actual
            cerrarVentanaRegistro();

            // Cargar ventana principal
            cargarVentanaPrincipal();

        }else {
            alertaError("Comprueba todos los campos e inténtalo nuevamente");
        }
    }

    public void cambiarImagen(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Imagen de Perfil");
        imagenFile = fileChooser.showOpenDialog(null);

        if (imagenFile == null) {
            File defaultImage = new File("src/main/resources/img/ProfilePicturePhoto.jpg");
            if (defaultImage.exists()) {
                imgUserEmpty.setImage(new Image(defaultImage.toURI().toString()));
            } else {
                System.out.println("La imagen predeterminada no se encuentra: " + defaultImage.getAbsolutePath());
            }
        } else if (imagenFile.exists()){
            imgUserEmpty.setImage(new Image(imagenFile.toURI().toString()));
        } else {
            System.out.println("La imagen seleccionada no se encuentra: " + imagenFile.getAbsolutePath());
        }
    }

    private boolean comprobaciones(){
        if (textNombre.getText().isBlank() || textPassword.getText().isBlank() || textConfPassw.getText().isBlank()) {
            // Mostrar labelErrorCampos si hay algún campo en blanco
            labelErrorCampos.setVisible(true);
            return false;
        } else {
            // Ocultar labelErrorCampos si los campos no están en blanco
            labelErrorCampos.setVisible(false);
        }

        if (!textPassword.getText().equals(textConfPassw.getText())) {
            // Mostrar labelErrorContraseñas si las contraseñas no coinciden
            labelErrorPassword.setVisible(true);
            return false;
        } else {
            // Ocultar labelErrorContraseñas si las contraseñas coinciden
            labelErrorPassword.setVisible(false);
        }
        if (GestionBD.existeUsuario(textNombre.getText())) {
            // Mostrar labelErrorUsuarioExistente si el usuario ya existe
            labelErrorUsuarioExist.setVisible(true);
            return false;
        } else {
            // Ocultar labelErrorUsuarioExistente si el usuario no existe
            labelErrorUsuarioExist.setVisible(false);
        }

        return true;
    }

    public void alertaError(String mensaje){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("No se puede crear la cuenta");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void cancelar(ActionEvent actionEvent) {
        cerrarVentanaRegistro();
        cargarVentanaPrincipal();
    }

    public void cerrarVentanaRegistro(){
        Stage stage = (Stage) botonCancelar.getScene().getWindow();
        stage.close();
    }

    private void cargarVentanaPrincipal() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/karlaconk/principal-view.fxml"));
            Parent root = fxmlLoader.load();
            Stage principalStage = new Stage();
            principalStage.setTitle("Karla Home");
            principalStage.setScene(new Scene(root));

            principalStage.initModality(Modality.APPLICATION_MODAL);

            PrincipalController principalController = fxmlLoader.getController();
            principalController.setRegistrarseController(this);
            principalStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
