package com.mymichi.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class databaseConnection {


    private static final String URL = "jdbc:mysql://localhost:3306/MyMichi";
    private static final String USUARIO = "localhost";
    private static final String CONTRASENA = "admin";

    public static Connection getConnection() throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
    }
}
