package com.example.karlaconk.modules;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GestionBD {

    public static void insertarUsuario(String nombre, String clave, File imagenFile) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Karla", "usuario", "contraseña")) {
            String sql = "INSERT INTO usuario (nombre_usuario, clave, imagen_usuario) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, nombre);
                stmt.setString(2, clave);

                // Verificar si se proporcionó un archivo de imagen
                if (imagenFile != null) {
                    // Convertir el archivo de imagen a bytes y establecer el valor del parámetro
                    stmt.setBytes(3, convertirImagenABytes(new FileInputStream(imagenFile)));
                } else {
                    // Utilizar la imagen por defecto si no se proporciona un archivo de imagen
                    stmt.setBytes(3, convertirImagenABytes(new FileInputStream(new File("src/main/resources/img/ProfilePicturePhoto.jpg"))));
                }

                // Ejecutar la inserción
                stmt.executeUpdate();
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace(); // Manejar la excepción de manera adecuada en tu aplicación
        }
    }

    // Método para convertir el InputStream de la imagen a bytes
    private static byte[] convertirImagenABytes(InputStream imagenStream) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = imagenStream.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }

            return bos.toByteArray();
        }
    }


}