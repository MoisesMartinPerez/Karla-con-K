package com.example.karlaconk.controllers;

import com.example.karlaconk.modules.GestionBD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
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


    private PrincipalController principalController;
    private InicioSesionController inicioSesionController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setPrincipalController(PrincipalController principalController) {
        this.principalController = principalController;
    }
    public void setInicioSesionController(InicioSesionController inicioSesionController){
        this.inicioSesionController = inicioSesionController;
    }

    public void crearCuenta(ActionEvent actionEvent) {
        String nombre = textNombre.getText();
        String password = textPassword.getText();
        String confPassw = textConfPassw.getText();

        // Validar que las contraseñas coincidan
        if (!password.equals(confPassw)) {
            // Mostrar un mensaje de error al usuario o realizar alguna acción
            return;
        }

        // Mostrar el diálogo de selección de archivo para que el usuario elija una imagen
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Imagen de Perfil");
        File imagenFile = fileChooser.showOpenDialog(null);

        // Llamar al método de inserción en la base de datos
        GestionBD.insertarUsuario(nombre, password, imagenFile);

        // Puedes realizar otras acciones después de crear la cuenta, como navegar a otra vista o mostrar un mensaje de éxito
    }


    public void cancelar(ActionEvent actionEvent) {
        Stage stage = (Stage) botonCancelar.getScene().getWindow();
        stage.close();
    }
}
