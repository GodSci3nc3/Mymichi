package com.mymichi.models;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
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

import com.mymichi.utils.databaseConnection;


@WebServlet("/loginServlet")
@MultipartConfig
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("usuario");
        String password = request.getParameter("password");

        Connection conn = null;

        /*Solo para debuguear */
        System.out.println("nombre:" + username);
        System.out.println("contraseña:" + password);
        try {
            conn = databaseConnection.getConnection();
            String sql = "SELECT * FROM usuario WHERE Nombre_Usuario = '" + username + "' AND Contraseña = '" + password + "'";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Datos correctos
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                response.sendRedirect("http://localhost:8080/mymichi/views/feed_view.jsp");
            } else {
                // Datos incorrectos
                request.setAttribute("error", "Usuario o contraseña incorrectos");
                request.getRequestDispatcher("http://localhost:8080/mymichi/views/login_view.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }
}