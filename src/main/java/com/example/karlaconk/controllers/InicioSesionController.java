package com.example.karlaconk.controllers;

import com.example.karlaconk.modules.GestionBD;
import com.example.karlaconk.modules.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controlador encargado de gestionar el inicio de sesión de usuarios.
 */
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

    /**
     * Establece el controlador principal para comunicación.
     *
     * @param principalController El controlador principal.
     */
    public void setPrincipalController(PrincipalController principalController) {
        this.principalController = principalController;
    }

    /**
     * Realiza la autenticación del usuario y gestiona el inicio de sesión.
     *
     * @param actionEvent El evento de acción que desencadenó la llamada al método.
     */
    public void iniciarSesion(ActionEvent actionEvent) {
        String nombreUsuario = textUsuario.getText();
        String password = textPassword.getText();

        // Comprobar si el usuario existe en la base de datos
        if (GestionBD.existeUsuario(nombreUsuario)) {
            // Obtener la información del usuario desde la base de datos
            Usuario usuario = GestionBD.obtenerUsuario(nombreUsuario);

            // Comprobar si la contraseña es correcta
            if (usuario != null && usuario.getClave().equals(password)) {
                // La contraseña es correcta, iniciar sesión
                Usuario.setSesionIniciada(true);
                // establecemos el ID del usuario actual
                Usuario.setIdUsuarioActual(usuario.getIdUsuario());

                // Configurar el usuario en PrincipalController
                if (principalController != null) {
                    principalController.setUsuario(usuario);
                }

                // Puedes realizar otras acciones después de iniciar sesión, como navegar a otra vista
                // o mostrar un mensaje de éxito
                System.out.println("Inicio de sesión exitoso");
                System.out.println("Nombre de usuario: " + usuario.getNombreUsuario());  // Agrega esta línea
                System.out.println(Usuario.isSesionIniciada());

                // Cerrar la ventana de inicio de sesión
                Stage stage = (Stage) botonIniciarS.getScene().getWindow();
                stage.close();

                cargarVentanaPrincipal();

            } else {
                // La contraseña es incorrecta
                alertaError("Contraseña incorrecta");
            }
        } else {
            // El usuario no existe
            alertaError("Usuario no registrado");
        }
    }

    public void registrar(ActionEvent actionEvent) {

        Stage stage = (Stage) botonRegistrarse.getScene().getWindow();
        stage.close();

        cargarVentanaRegistro();
    }

    public void alertaError(String mensaje){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("No se puede crear la cuenta");
        alert.setContentText(mensaje);
        alert.showAndWait();
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
            registrarseController.setInicioSesionController(this);
            registrarseStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
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
            principalController.setInicioSesionController(this);
            principalStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}