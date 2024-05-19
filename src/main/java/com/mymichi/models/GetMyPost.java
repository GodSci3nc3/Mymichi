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

@WebServlet("/getMyPost")
public class GetMyPost extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Entró al servlet");
        List<Publicacion> publicaciones = new ArrayList<>();
        Connection conn = null;
        try {
            conn = databaseConnection.getConnection();

            // 1. Obtener el ID_Usuario del usuario actual con session
            HttpSession session = request.getSession();
            int idUsuarioActual = (int) session.getAttribute("idUsuario"); 

            System.out.println("Antes de hacer la primer consulta");

            String sqlUsuario = "SELECT * FROM usuario WHERE ID_Usuario = ?";
            PreparedStatement statementUsuario = conn.prepareStatement(sqlUsuario);
            statementUsuario.setInt(1, idUsuarioActual);
            ResultSet resultSetUsuario = statementUsuario.executeQuery();

            if (resultSetUsuario.next()) {
                String nombreUsuario = resultSetUsuario.getString("Nombre_Usuario");
                byte[] imagenPerfil = resultSetUsuario.getBytes("Imagen_Perfil");

                System.out.println("Pasó la primera. Antes de la segunda");

                // 2. Obtener todas las publicaciones del usuario actual
                String sql = "SELECT * FROM publicación WHERE ID_Usuario = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1, idUsuarioActual);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    Publicacion publicacion = new Publicacion();
                    publicacion.setId(resultSet.getInt("ID_Publición"));
                    publicacion.setTitulo(resultSet.getString("Titulo"));
                    publicacion.setContenido(resultSet.getString("Contenido"));
                    publicacion.setImagen(resultSet.getBytes("Imagen"));
                    publicacion.setIdUsuario(idUsuarioActual);
                    publicacion.setIdCategoria(resultSet.getInt("ID_Categoria"));
                    publicacion.setNombreUsuario(nombreUsuario);
                    publicacion.setImagenUsuario(imagenPerfil);

                    // 3. Obtener la categoría
                    int idCategoria = publicacion.getIdCategoria();

                    System.out.println("Pasó la segunda. Antes de la tercera");
                    String sqlCategoria = "SELECT Categoria FROM categoria WHERE ID_Categoria = ?";
                    PreparedStatement statementCategoria = conn.prepareStatement(sqlCategoria);
                    statementCategoria.setInt(1, idCategoria);
                    ResultSet resultSetCategoria = statementCategoria.executeQuery();
                    if (resultSetCategoria.next()) {
                        publicacion.setCategoria(resultSetCategoria.getString("Categoria"));
                    }

                    publicaciones.add(publicacion);
                }

                System.out.println("Pasó todas");

                // 4. Guardar las publicaciones en la sesión
                session.setAttribute("mispublicaciones", publicaciones);
                System.out.println("Pasó todo");
                response.sendRedirect("views/profile_view.jsp");

                
            } else {
                // No se encontró el usuario actual
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Usuario no encontrado");
            }
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