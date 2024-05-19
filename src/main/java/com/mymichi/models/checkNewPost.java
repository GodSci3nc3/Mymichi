package com.mymichi.models;

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
import java.util.List;
import com.mymichi.utils.databaseConnection;
import com.mymichi.utils.Publicacion;

@WebServlet("/checkNewPosts")
public class checkNewPost extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Publicacion> publicaciones = (List<Publicacion>) session.getAttribute("publicaciones");

        if (publicaciones == null || publicaciones.isEmpty()) {
            // Si no hay publicaciones en la sesión, no se pueden comparar, así que no hay nuevas publicaciones.
            response.getWriter().write("No hay nuevas publicaciones.");
            return;
        }

        // Obtener el número total de publicaciones en la base de datos
        int totalPublicacionesBD = getTotalPublicacionesBD();

        // Comparar con la cantidad de publicaciones en la sesión
        if (totalPublicacionesBD > publicaciones.size()) {
            response.getWriter().write("¡Hay nuevas publicaciones!");
        } else {
            response.getWriter().write("No hay nuevas publicaciones.");
        }
    }

    // Método para obtener el número total de publicaciones en la base de datos
    private int getTotalPublicacionesBD() {
        int totalPublicaciones = 0;
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conn = databaseConnection.getConnection();
            String query = "SELECT COUNT(*) FROM publicacion";
            statement = conn.prepareStatement(query);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                totalPublicaciones = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar recursos
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return totalPublicaciones;
    }
}
