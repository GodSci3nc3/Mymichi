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

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Publicacion> publicaciones = (List<Publicacion>) session.getAttribute("publicaciones");

        if (publicaciones == null || publicaciones.isEmpty()) {
            response.sendRedirect("/mymichi/getPost");
            return;
        } else {
            Connection conn = null;
            try {
                conn = databaseConnection.getConnection();
                String sql = "SELECT COUNT(*) FROM publicación";
                PreparedStatement statement = conn.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    if (publicaciones.size() < resultSet.getInt(1)) {
                        response.sendRedirect("/mymichi/getPost");
                    } else { response.sendRedirect("views/feed_view.jsp"); }
                            
                }
            
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
