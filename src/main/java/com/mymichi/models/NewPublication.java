package com.mymichi.models;



import com.mymichi.utils.databaseConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/newPost")
@MultipartConfig
public class NewPublication extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Entrando al servlet NewPublication");

    
        // Se extraen los datos del formulario
        String titulo = request.getParameter("title");
        String contenido = request.getParameter("content");
        String categoria = request.getParameter("category");


        // Se extrae la imagen
        Part filePart = request.getPart("image");
        InputStream inputStream = null;
        if (filePart != null) {
            inputStream = filePart.getInputStream();
        }


        Connection conn = null;
        try {
            conn = databaseConnection.getConnection();

            // Obtener el id_usuario de session
            HttpSession session = request.getSession();
            String username = (String) session.getAttribute("username");
            String correo = (String) session.getAttribute("correo");
            int idUsuario = obtenerIdUsuario(conn, username, correo);

            // Obtener el id_categoria
            int idCategoria = obtenerIdCategoria(conn, categoria);

            System.out.println("Antes de realizar la consulta");


            String sql = "INSERT INTO publicación (Titulo, Contenido, ID_Categoria, Imagen, ID_Usuario, Estado) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, titulo);
            statement.setString(2, contenido);
            statement.setInt(3, idCategoria);
            if (inputStream != null) {
                statement.setBinaryStream(4, inputStream);
            } else {
                statement.setBytes(4, null);
            }
            statement.setInt(5, idUsuario);
            statement.setInt(6, 1); // Estado = 1 (activo)

            statement.executeUpdate();

            response.sendRedirect("views/feed_view.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private int obtenerIdUsuario(Connection conn, String username, String correo) throws SQLException {
        String sql = "SELECT ID_Usuario FROM usuario WHERE Nombre_Usuario = ? AND Correo = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, username);
        statement.setString(2, correo);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("ID_Usuario");
        }
        return -1; // No se encontró el usuario
    }

    private int obtenerIdCategoria(Connection conn, String categoria) throws SQLException {
        String sql = "SELECT ID_Categoria FROM categoria WHERE Categoria = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, categoria);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("ID_Categoria");
        }
        return -1; // No se encontró la categoría
    }
}