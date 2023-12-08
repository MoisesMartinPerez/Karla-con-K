package com.example.karlaconk.modules;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.karlaconk.modules.Conexion.getConnection;

public class GestionBD {

    public static void insertarUsuario(String nombre, String clave, File imagenFile) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Karla", "root", "1234")) {
            String sql = "INSERT INTO usuario (nombre_usuario, clave, imagen_usuario) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, nombre);
                stmt.setString(2, clave);

                // Verificar si se proporcionó un archivo de imagen
                if (imagenFile != null) {
                    // Convertir el archivo de imagen a bytes y establecer el valor del parámetro
                    try {
                        stmt.setBytes(3, convertirImagenABytes(new FileInputStream(imagenFile)));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    // Utilizar la imagen por defecto si no se proporciona un archivo de imagen
                    File imagenPorDefecto = new File("src/main/resources/img/ProfilePicturePhoto.jpg");
                    try {
                        stmt.setBytes(3, convertirImagenABytes(new FileInputStream(imagenPorDefecto)));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }


                // Ejecutar la inserción
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
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

    public static boolean existeUsuario(String nombreUsuario) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Karla", "root", "1234")) {
            String sql = "SELECT COUNT(*) FROM usuario WHERE nombre_usuario = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, nombreUsuario);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        int count = rs.getInt(1);
                        return count > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejar la excepción de manera adecuada en tu aplicación
        }
        return false; // Manejar el caso de error de manera adecuada
    }

    public static Usuario obtenerUsuario(String nombreUsuario) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Karla", "root", "1234")) {
            String sql = "SELECT * FROM usuario WHERE nombre_usuario = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, nombreUsuario);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        int idUsuario = rs.getInt("id_usuario");
                        String clave = rs.getString("clave");
                        byte[] imagenUsuario = rs.getBytes("imagen_usuario");

                        // Agrega mensajes de depuración
                        System.out.println("ID de usuario: " + idUsuario);
                        System.out.println("Clave: " + clave);
                        System.out.println("Longitud de la imagen: " + imagenUsuario.length);


                        return new Usuario(idUsuario, nombreUsuario, clave, imagenUsuario);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejar la excepción de manera adecuada en tu aplicación
        }
        return null; // Manejar el caso de error de manera adecuada
    }

    public static void eliminarUsuario(String nombreUsuario) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Karla", "root", "1234")) {
            // Eliminar registros relacionados en otras tablas (puedes adaptar esto según tus necesidades)
            eliminarRegistrosRelacionados(conn, nombreUsuario, "favoritos");
            eliminarRegistrosRelacionados(conn, nombreUsuario, "historial_reproduccion");
            eliminarRegistrosRelacionados(conn, nombreUsuario, "listas_reproduccion");

            // Eliminar al usuario
            String sql = "DELETE FROM usuario WHERE nombre_usuario = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, nombreUsuario);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void eliminarRegistrosRelacionados(Connection conn, String nombreUsuario, String tabla) throws SQLException {
        // Eliminar registros relacionados en la tabla especificada
        String sql = "DELETE FROM " + tabla + " WHERE id_usuario = (SELECT id_usuario FROM usuario WHERE nombre_usuario = ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombreUsuario);
            stmt.executeUpdate();
        }
    }


    //para cambiar los datos
    public static void actualizarUsuario(String nombreUsuario, String nuevoNombre, String nuevaClave) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Karla", "root", "1234")) {
            // Construir la consulta de actualización
            StringBuilder sql = new StringBuilder("UPDATE usuario SET ");
            List<String> parametros = new ArrayList<>();

            if (!nuevoNombre.isEmpty()) {
                parametros.add("nombre_usuario = ?");
            }

            if (!nuevaClave.isEmpty()) {
                parametros.add("clave = ?");
            }

            sql.append(String.join(", ", parametros));
            sql.append(" WHERE nombre_usuario = ?");

            try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
                int index = 1;

                if (!nuevoNombre.isEmpty()) {
                    stmt.setString(index++, nuevoNombre);
                }

                if (!nuevaClave.isEmpty()) {
                    stmt.setString(index++, nuevaClave);
                }

                stmt.setString(index, nombreUsuario);

                // Ejecutar la actualización
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejar la excepción de manera adecuada en tu aplicación
        }
    }

    /**
     * metodo para insertar la lista de reproduccion en la base de datos
     * */
    public static void insertarListaReproduccion(String nombreLista, int idUsuario) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Karla", "root", "1234")) {
            String sql = "INSERT INTO listas_reproduccion (nombre_lista, id_usuario) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, nombreLista);
                stmt.setInt(2, idUsuario);
                stmt.executeUpdate();

                // obtenemos el ID de la lista de reproducción recién creada
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int idLista = generatedKeys.getInt(1);
                        // imprimimos o utilizar el ID para comprobar
                        System.out.println("ID de la nueva lista de reproducción: " + idLista);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *  metodo para modificar el nombre de una playlist en la base de datos
     * */
    public static void actualizarNombrePlaylist(int idLista, String nuevoNombre) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Karla", "root", "1234")) {
            String sql = "UPDATE listas_reproduccion SET nombre_lista = ? WHERE id_lista = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, nuevoNombre);
                stmt.setInt(2, idLista);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *  metodo para eliminar una playlist de la base de datos
     * */
    public static void eliminarPlaylist(int idLista) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Karla", "root", "1234")) {
            // eliminamos los registros relacionados en la tabla canciones_lista
            String sqlEliminarCancionesLista = "DELETE FROM canciones_lista WHERE id_lista = ?";
            try (PreparedStatement stmtEliminarCancionesLista = conn.prepareStatement(sqlEliminarCancionesLista)) {
                stmtEliminarCancionesLista.setInt(1, idLista);
                stmtEliminarCancionesLista.executeUpdate();
            }

            // eliminamos la playlist de labase de datos
            String sqlEliminarPlaylist = "DELETE FROM listas_reproduccion WHERE id_lista = ?";
            try (PreparedStatement stmtEliminarPlaylist = conn.prepareStatement(sqlEliminarPlaylist)) {
                stmtEliminarPlaylist.setInt(1, idLista);
                stmtEliminarPlaylist.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * metodopara cargar las playlist del usuarioactual
     * */
    public static ObservableList<Playlist> obtenerListasReproduccionUsuario(int idUsuario) {
        ObservableList<Playlist> listasReproduccion = FXCollections.observableArrayList();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Karla", "root", "1234")) {
            String sql = "SELECT * FROM listas_reproduccion WHERE id_usuario = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, idUsuario);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        int idLista = rs.getInt("id_lista");
                        String nombreLista = rs.getString("nombre_lista");

                        Playlist playlist = new Playlist(idLista, nombreLista, idUsuario);
                        listasReproduccion.add(playlist);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listasReproduccion;
    }


    public static byte[] obtenerImagenUsuario(String nombreUsuario) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Karla", "root", "1234")) {
            String sql = "SELECT imagen_usuario FROM usuario WHERE nombre_usuario = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, nombreUsuario);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getBytes("imagen_usuario");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
