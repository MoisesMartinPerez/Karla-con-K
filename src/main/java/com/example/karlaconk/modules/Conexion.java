package com.example.karlaconk.modules;

import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion {

    public static Connection connection;
    private static final String user= "root";
    private static final String pass="1234";
    private static final String url="jdbc:mysql://localhost:3306/Karla";


    public static Connection getConnection(){
        return connection;
    }

    public void conectar () {

        try{
            connection = DriverManager. getConnection(url, user, pass);
            System.out.println("Conectado");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static boolean ingresar(String nombre, String password){
        String sql = "insert into clientes ( nombre_usuario, clave) values (?, ?)";

//        try{  //metodo comprobacion para ver si esta correcto
//
//            String nombre = textNombre.getText();
//            String password = textPassword.getText();
//            String confPassword = textConfPassw.getText();
//
//            if (nombre.isEmpty() || password.isEmpty() || confPassword.isEmpty()) {
//                Alert a  = new Alert(Alert.AlertType.ERROR);
//                a.setContentText("ERROR! Rellena todos los campos.");
//                a.setTitle("Registrar usuario");
//                a.setHeaderText(null);
//                a.showAndWait();
//                return;
//            }
//
//
//
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
        try{
            PreparedStatement sentencia = connection.prepareStatement(sql);
            sentencia.setString(1, nombre);
            sentencia.setString(2, password);
            sentencia.execute();

        } catch (SQLException e) {
            return false;
        }
        return true;

    }

    public void desconectar(){
        try{
            connection.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }


}
