package com.example.karlaconk.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {
    @FXML
    public Button botonRegistrar;
    @FXML
    public Button botonInicioSesion;


    public void registrar(ActionEvent actionEvent) {
        cargarVentanaRegistro();
    }

    public void iniciarSesion(ActionEvent actionEvent) {
        cargarVentanaInicioSesion();
    }

    private void cargarVentanaRegistro() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("com/example/karlaconk/registrarse-view.fxml"));
            Parent root = fxmlLoader.load();
            Stage registrarseStage = new Stage();
            registrarseStage.setTitle("Registro de Usuario");
            registrarseStage.setScene(new Scene(root));

            registrarseStage.initModality(Modality.APPLICATION_MODAL);

            RegistrarseController registrarseController = fxmlLoader.getController();
            registrarseController.setHomeController(this);
            registrarseStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarVentanaInicioSesion(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("com/example/karlaconk/iniciarSesion-view.fxml"));
            Parent root = fxmlLoader.load();
            Stage inicioStage = new Stage();
            inicioStage.setTitle("Inicio de Sesión");
            inicioStage.setScene(new Scene(root));

            inicioStage.initModality(Modality.APPLICATION_MODAL);

            InicioSesionController inicioSesionController = fxmlLoader.getController();
            inicioSesionController.setHomeController(this);
            inicioStage.showAndWait();
        }catch (IOException e){
            e.printStackTrace();
        }
    }



}