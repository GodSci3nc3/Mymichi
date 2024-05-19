package com.mymichi.models;

import com.mymichi.utils.Publicacion;
import com.mymichi.utils.databaseConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/getPost")
public class getPostsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Publicacion> publicaciones = new ArrayList<>();

        Connection conn = null;
        try {
            conn = databaseConnection.getConnection();

            // 1. Obtener las publicaciones activas
            String sql = "SELECT * FROM publicación WHERE Estado = 1";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Publicacion publicacion = new Publicacion();
                publicacion.setId(resultSet.getInt("ID_Publición"));
                publicacion.setTitulo(resultSet.getString("Titulo"));
                publicacion.setContenido(resultSet.getString("Contenido"));
                publicacion.setImagen(resultSet.getBytes("Imagen"));
                publicacion.setIdUsuario(resultSet.getInt("ID_Usuario"));
                publicacion.setIdCategoria(resultSet.getInt("ID_Categoria"));

                // 2. Obtener el nombre de usuario y la imagen de perfil
                int idUsuario = publicacion.getIdUsuario();
                String sqlUsuario = "SELECT Nombre_Usuario, Imagen_Perfil FROM usuario WHERE ID_Usuario = ?";
                PreparedStatement statementUsuario = conn.prepareStatement(sqlUsuario);
                statementUsuario.setInt(1, idUsuario);
                ResultSet resultSetUsuario = statementUsuario.executeQuery();

                if (resultSetUsuario.next()) {
                    publicacion.setNombreUsuario(resultSetUsuario.getString("Nombre_Usuario"));
                    publicacion.setImagenUsuario(resultSetUsuario.getBytes("Imagen_Perfil"));
                }

                // 3. Obtener la categoría
                int idCategoria = publicacion.getIdCategoria();
                String sqlCategoria = "SELECT Categoria FROM categoria WHERE ID_Categoria = ?";
                PreparedStatement statementCategoria = conn.prepareStatement(sqlCategoria);
                statementCategoria.setInt(1, idCategoria);
                ResultSet resultSetCategoria = statementCategoria.executeQuery();

                if (resultSetCategoria.next()) {
                    publicacion.setCategoria(resultSetCategoria.getString("Categoria"));
                }

                publicaciones.add(publicacion);
            }

            HttpSession session = request.getSession();
            session.setAttribute("publicaciones", publicaciones);

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
}