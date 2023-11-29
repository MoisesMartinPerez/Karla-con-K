package com.example.karlaconk.controllers;

import com.example.karlaconk.modules.Conexion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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


    private HomeController homeController;
    private InicioSesionController inicioSesionController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }
    public void setInicioSesionController(InicioSesionController inicioSesionController){
        this.inicioSesionController = inicioSesionController;
    }

    public void crearCuenta(ActionEvent actionEvent) {
        //corregir
        //Conexion.ingresar(textNombre.getText(), textPassword.getText(), textConfPassw.getText());

    }

    public void cancelar(ActionEvent actionEvent) {

    }
}
