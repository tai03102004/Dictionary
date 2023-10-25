package com.example.directory.Models;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public Connection databaseLink;
    public Connection getConnection() {
        String databaseName = "";
        String databaseUser = "";
        String databasePassword = "";
        String databasePhone = "";
        String databaseEmail = "";
        String databaseSex = "";
        String url = "jdbc:mysql://localhost/" + databaseName;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url,databaseUser,databasePassword);

        } catch (Exception e){
            e.printStackTrace();
        }
        return databaseLink;
    }
}
