package com.lopez.app.restaurante.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    private  static  String url = "jdbc:oracle:thin:@//127.0.0.1:1521/xe";
    private static  String username= "DAVID";
    private  static  String password = "root";


    public  static Connection getInstance() {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
