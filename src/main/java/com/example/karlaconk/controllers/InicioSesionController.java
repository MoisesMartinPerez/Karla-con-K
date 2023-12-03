package com.example.karlaconk.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class InicioSesionController {
    @FXML
    public Button botonIniciarS;
    @FXML
    public Hyperlink botonRegistrarse;
    @FXML
    public PasswordField textPassword;
    @FXML
    public TextField textUsuario;
    private PrincipalController principalController;

    public void setPrincipalController(PrincipalController principalController) {
        this.principalController = principalController;
    }

    public void iniciarSesion(ActionEvent actionEvent) {

    }

    public void registrar(ActionEvent actionEvent) {
        cargarVentanaRegistro();
    }

    private void cargarVentanaRegistro() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/karlaconk/registrarse-view.fxml"));
            Parent root = fxmlLoader.load();
            Stage registrarseStage = new Stage();
            registrarseStage.setTitle("Registro de Usuario");
            registrarseStage.setScene(new Scene(root));
            //commit de prueba
            registrarseStage.initModality(Modality.APPLICATION_MODAL);

            RegistrarseController registrarseController = fxmlLoader.getController();
            registrarseController.setInicioSesionController(this);
            registrarseStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}