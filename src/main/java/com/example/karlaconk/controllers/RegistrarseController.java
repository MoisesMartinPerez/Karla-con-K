package com.example.karlaconk.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegistrarseController {
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



    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }
    public void setInicioSesionController(InicioSesionController inicioSesionController){
        this.inicioSesionController = inicioSesionController;
    }

    public void crearCuenta(ActionEvent actionEvent) {
        //Karla.ingresar(textNombre.getText(), textPassword.getText(), textConfPassw.getText());
    }

    public void cancelar(ActionEvent actionEvent) {

    }
}
