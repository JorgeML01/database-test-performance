package com.mycompany.databaseperformancetest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class MySQLDatabase extends DatabaseManager {
    
    private Connection connection;

    @Override
    public void insert(String tableName, int cantidadRegistros) {

    }

    @Override
    public void truncate(String tableName) {

    }

    @Override
    public void connect() {
        String jdbcUrl = "jdbc:mysql://localhost:3306/teoria";
        String username = "root";
        String password = "contra";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(jdbcUrl, username, password);

            System.out.println("Connected to the database!");

            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Disconnected from the database.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
