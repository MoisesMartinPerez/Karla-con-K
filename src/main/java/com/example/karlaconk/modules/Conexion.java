package com.example.karlaconk.modules;

import java.sql.Connection;
import java.sql.DriverManager;
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

    public void desconectar(){
        try{
            connection.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }


}
