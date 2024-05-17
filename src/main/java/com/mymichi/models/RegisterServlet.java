package com.mymichi.models;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import com.mymichi.utils.databaseConnection;


@WebServlet("/registerServlet")
@MultipartConfig
public class RegisterServlet extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Si hace falta añadir, modificar o borrar datos procesables del usuario, es
        // aqui
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String correo = request.getParameter("correo");
        String nombreUsuario = request.getParameter("nombre_usuario");
        String contrasena = request.getParameter("password");
        Part filePart = request.getPart("image"); // Retiene la imagen
        // Retiene: 1. fecha de nacimiento 2. fecha de registro 
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date parsedDate;

        java.util.Date fechaRegistro = new java.util.Date();
        Timestamp fechaRegistroTimeStamp = new Timestamp(fechaRegistro.getTime());


        try {

            parsedDate = dateFormat.parse(request.getParameter("fecha_nacimiento"));
        } catch (ParseException e) {
            e.printStackTrace();
            // En caso de que no se pudiese sacar la fecha, no sé qué haría
            return;
        }

        // 3. edad calculada con la fecha de nacimiento
        java.sql.Date fechaNacimiento = new java.sql.Date(parsedDate.getTime());
        LocalDate fechaNacimientoLocal = fechaNacimiento.toLocalDate();

        LocalDate fechaActual = LocalDate.now();

        Period periodo = Period.between(fechaNacimientoLocal, fechaActual);

        int edad = periodo.getYears();



        // Imagen
        InputStream inputStream = null; 
        if (filePart != null) {
            inputStream = filePart.getInputStream();
        }

        /*Solo para debuguear 
        System.out.println("nombre:" + nombre);
        System.out.println("apellido:" + apellido);
        System.out.println("nació en:" + fechaNacimiento);
        System.out.println("email:" + correo);
        System.out.println("nickname:" + nombreUsuario);
        System.out.println("pass:" + contrasena);
        System.out.println("foto:" + inputStream);
        System.out.println("fecha de registro:" + fechaRegistroTimeStamp);
        System.out.println("Edad:" + edad);*/

        Connection conn = null;

        // 2. Validación de datos (aún en desarrollo)

        // 3. Llamar a la base de datos
        try {
            conn = databaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 4. Insertar datos en la base de datos
        String sql = "INSERT INTO usuario (Nombre_Usuario, Correo, Contraseña, Nombres, Apellidos, Fecha_Nacimiento, Imagen_Perfil, Edad, Fecha_Registro) VALUES ('" + nombreUsuario + "', '" + correo + "', '" + contrasena + "', '" + nombre + "', '" + apellido + "', '" + fechaNacimiento + "', '" + inputStream + "', '" + edad + "', '" + fechaRegistroTimeStamp + "')";
        

        PreparedStatement statement = null;
        
        try {
            statement = conn.prepareStatement(sql);

            int rows = statement.executeUpdate();

            if(rows > 0){
                HttpSession session = request.getSession();

                byte[] imageBytes = null;
                if (inputStream != null) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = inputStream.read(buffer)) != -1) {
                        baos.write(buffer, 0, len);
                    }
                    imageBytes = baos.toByteArray();
                    inputStream.close();
                }


                session.setAttribute("username", nombreUsuario);
                session.setAttribute("photo", imageBytes);
                session.setAttribute("correo", correo);
                session.setAttribute("edad", edad);

                
                response.sendRedirect("views/feed_view.jsp");
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
