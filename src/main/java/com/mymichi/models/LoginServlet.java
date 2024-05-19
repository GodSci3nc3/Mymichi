package com.mymichi.models;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mymichi.utils.databaseConnection;

@WebServlet("/loginServlet")
@MultipartConfig
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("usuario");
        String password = request.getParameter("password");
        Connection conn = null;
        try {
            conn = databaseConnection.getConnection();
            String sql = "SELECT * FROM usuario WHERE Nombre_Usuario = ? AND Contraseña = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // Datos correctos:
                HttpSession session = request.getSession();
                // 1. Se guarda el nombre de usuario en session
                session.setAttribute("username", username);

                // 2. Se guarda la edad en session
                int edad = resultSet.getInt("edad");
                session.setAttribute("edad", edad);

                // 3. Se guarda el correo en session
                String correo = resultSet.getString("Correo");
                session.setAttribute("correo", correo);

                // 4. Obtener la foto del usuario
                InputStream inputStream = null;
                ResultSet rs = null;
                ByteArrayOutputStream outputStream = null;

                try {
                    inputStream = resultSet.getBinaryStream("Imagen_Perfil");
                    outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead = -1;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    byte[] fotoBytes = outputStream.toByteArray();
                    // 5. Se guarda la foto en session
                    session.setAttribute("photo", fotoBytes);
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (resultSet != null) {
                        resultSet.close();
                    }
                    if (outputStream != null) {
                        outputStream.close();
                    }
                }

                response.sendRedirect("getPost");
            } else {
                // Datos incorrectos
                request.setAttribute("errorMessage", "Nombre de usuario o contraseña incorrectos");
                request.getRequestDispatcher("/views/login_view.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}