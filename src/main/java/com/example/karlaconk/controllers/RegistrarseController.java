package com.example.karlaconk.controllers;

import com.example.karlaconk.modules.Conexion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.swing.*;
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
        try{

            String nombre = textNombre.getText();
            String password = textPassword.getText();
            String confPassword = textConfPassw.getText();

            if (nombre.isEmpty() || password.isEmpty() || confPassword.isEmpty()) {
                Alert a  = new Alert(Alert.AlertType.ERROR);
                a.setContentText("ERROR! Rellena todos los campos.");
                a.setTitle("Registrar usuario");
                a.setHeaderText(null);
                a.showAndWait();
                return;
            }
            Conexion.getConnection();

            String sql = "INSERT INTO ";
            //Karla.ingresar(textNombre.getText(), textPassword.getText(), textConfPassw.getText());
        }catch (Exception e) {
                e.printStackTrace();
        }

    }

    public void cancelar(ActionEvent actionEvent) {

    }
}
