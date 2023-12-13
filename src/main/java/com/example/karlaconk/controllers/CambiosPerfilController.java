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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controlador encargado de gestionar la modificación de datos del perfil de usuario.
 */
public class CambiosPerfilController implements Initializable {
    @FXML
    public Button buttonImg;
    @FXML
    public ImageView imgView;
    @FXML
    public Label labelNombreUsuario;
    @FXML
    public Label labelClave;
    @FXML
    public Label labelCerrarSesion;
    @FXML
    public Label labelEliminarCuenta;
    @FXML
    public TextField textNombreUsuario;
    @FXML
    public TextField textNuevaClave;
    @FXML
    public Button buttonConfirmarCambios;
    @FXML
    public Button buttonDescartarCambios;

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
     * Inicializa el controlador.
     *
     * @param url             La ubicación del archivo FXML.
     * @param resourceBundle Los recursos utilizados para localizar el archivo FXML.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialize CambiosPerfilController");
        buttonConfirmarCambios.setVisible(false);
        textNuevaClave.setVisible(false);
        textNombreUsuario.setVisible(false);
    }

    /**
     * Muestra la interfaz para cambiar el nombre del usuario.
     *
     * @param mouseEvent El evento del mouse que desencadenó la llamada al método.
     */
    public void cambiarNombre(MouseEvent mouseEvent) {
        textNombreUsuario.setVisible(true);
        buttonConfirmarCambios.setVisible(true);

    }

    /**
     * Cierra la sesión del usuario.
     *
     * @param mouseEvent El evento del mouse que desencadenó la llamada al método.
     */
    public void cerrarSesion(MouseEvent mouseEvent) {
        String mensaje = "¿Estás seguro de que quieres cerrar sesión?";
        if (mostrarConfirmacion(mensaje)) {
            Usuario.setSesionIniciada(false);
            System.out.println("Sesión cerrada");
            System.out.println(Usuario.isSesionIniciada());

            // cerrar ventana
            Stage stage = (Stage) labelCerrarSesion.getScene().getWindow();
            stage.close();

            cargarVentanaPrincipal();
        }
    }

    /**
     * Elimina la cuenta del usuario.
     *
     * @param mouseEvent El evento del mouse que desencadenó la llamada al método.
     */
    public void eliminarCuenta(MouseEvent mouseEvent) {
        String mensaje = "¿Estás seguro de que quieres eliminar tu cuenta?";
        if (mostrarConfirmacion(mensaje)) {
            // Obtener el nombre de usuario actual
            String nombreUsuario = textNombreUsuario.getText();

            // Cerrar la sesión
            Usuario.setSesionIniciada(false);

            // Eliminar la cuenta de la base de datos
            GestionBD.eliminarUsuario(nombreUsuario);

            mostrarInformacion("La cuenta ha sido eliminada");

            cargarVentanaPrincipal();
        }
    }

    /**
     * Confirma los cambios realizados en el perfil del usuario.
     *
     * @param actionEvent El evento de acción que desencadenó la llamada al método.
     */
    public void confirmarCambios(ActionEvent actionEvent) {
        // Obtener el nombre de usuario actual
        String nombreUsuario = textNombreUsuario.getText();
        String nuevaClave = textNuevaClave.getText();

        Usuario usuario = principalController.getUsuario();

        if (usuario != null) {
            // Actualizar el nombre de usuario y/o clave
            GestionBD.actualizarUsuario(usuario.getNombreUsuario(), nombreUsuario, nuevaClave);

            // Actualizar la interfaz gráfica
            usuario.setNombreUsuario(nombreUsuario);

            // Mostrar mensaje de éxito o realizar otras acciones necesarias
            mostrarInformacion("Cambios aplicados correctamente.");
        } else {
            // Manejar el caso en el que usuario sea nulo
            mostrarInformacion("No se pudo obtener el usuario para realizar los cambios.");
        }
        // Ocultar campos y botón de confirmar cambios
        textNombreUsuario.setVisible(false);
        textNuevaClave.setVisible(false);
        buttonConfirmarCambios.setVisible(false);
        cargarVentanaPrincipal();
    }

    private void mostrarInformacion(String mensaje) {
        Alert informacion= new Alert(Alert.AlertType.INFORMATION);
        informacion.setTitle("Información");
        informacion.setHeaderText(mensaje);
    }

    public void cambiarImg(ActionEvent actionEvent) {


    }

    /**
     * Descarta los cambios realizados y vuelve a la ventana principal.
     *
     * @param actionEvent El evento de acción que desencadenó la llamada al método.
     */
    public void decartarCambios(ActionEvent actionEvent) {
        mostrarConfirmacion("¿Seguro que quiere volver? \n " +
                "\n No se guardarán los cambios introducidos");

        cargarVentanaPrincipal();
    }

    /**
     * Muestra la interfaz para cambiar la clave del usuario.
     *
     * @param mouseEvent El evento del mouse que desencadenó la llamada al método.
     */
    public void cambiarClave(MouseEvent mouseEvent) {
        textNuevaClave.setVisible(true);
        buttonConfirmarCambios.setVisible(true);
    }

    private boolean mostrarConfirmacion(String mensaje) {
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmación");
        confirmacion.setHeaderText(mensaje);

        Optional<ButtonType> resultado = confirmacion.showAndWait();
        return resultado.isPresent() && resultado.get() == ButtonType.OK;
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
            principalController.setCambiosPerfilController(this);
            principalStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
