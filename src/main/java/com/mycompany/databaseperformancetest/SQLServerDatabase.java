package com.mycompany.databaseperformancetest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SQLServerDatabase extends DatabaseManager {

    private Connection connection;

    @Override
    public void insert(String tableName, int cantidadRegistros) {

    }

    @Override
    public void truncate(String tableName) {
        try {
            Statement statement = connection.createStatement();

            // Construir y ejecutar la consulta SQL de truncamiento
            String sql = "TRUNCATE TABLE " + tableName;
            statement.executeUpdate(sql);

            System.out.println("La tabla " + tableName + " ha sido truncada exitosamente.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void connect() {
        String jdbcUrl = "jdbc:jtds:sqlserver://DESKTOP-02SPIHT:1433/master;instance=MSSQLSERVER";
        String username = "larap";
        String password = "larap";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(jdbcUrl, username, password);

            System.out.println("Connected to the database!");

        } catch (ClassNotFoundException | SQLException e) {
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

    public ArrayList<String> getTableNames() {
        // Crear un ArrayList para almacenar los nombres de las tablas
        ArrayList<String> tableNames = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();

            // Construir y ejecutar la consulta SQL para obtener los nombres de las tablas
            String sql = "SELECT name FROM sys.tables";
            statement.executeQuery(sql);

            // Recorrer los resultados de la consulta
            while (statement.getResultSet().next()) {
                // Obtener el nombre de la tabla actual
                String tableName = statement.getResultSet().getString("name");

                // Agregar el nombre de la tabla al ArrayList
                tableNames.add(tableName);
            }

            // Cerrar el Statement
            statement.close();

            // Retornar el ArrayList con los nombres de las tablas
            return tableNames;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Retornar null si ocurre un error
        return null;
    }

    public ArrayList<String> getTableFields(String tableName) {
        // Crear un ArrayList para almacenar los nombres de los campos
        ArrayList<String> fieldNames = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();

            // Construir y ejecutar la consulta SQL para obtener los nombres de los campos
            String sql = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '" + tableName + "'";
            statement.executeQuery(sql);

            // Recorrer los resultados de la consulta
            while (statement.getResultSet().next()) {
                // Obtener el nombre del campo actual
                String fieldName = statement.getResultSet().getString("COLUMN_NAME");

                // Agregar el nombre del campo al ArrayList
                fieldNames.add(fieldName);
            }

            // Cerrar el Statement
            statement.close();

            // Retornar el ArrayList con los nombres de los campos
            return fieldNames;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Retornar null si ocurre un error
        return null;
    }

    public ArrayList<String> getFieldDataTypes(String tableName) {
        // Crear un ArrayList para almacenar los tipos de datos de los campos
        ArrayList<String> fieldDataTypes = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();

            // Construir y ejecutar la consulta SQL para obtener los tipos de datos de los campos
            String sql = "SELECT DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '" + tableName + "'";
            statement.executeQuery(sql);

            // Recorrer los resultados de la consulta
            while (statement.getResultSet().next()) {
                // Obtener el tipo de dato del campo actual
                String fieldDataType = statement.getResultSet().getString("DATA_TYPE");

                // Agregar el tipo de dato del campo al ArrayList
                fieldDataTypes.add(fieldDataType);
            }

            // Cerrar el Statement
            statement.close();

            // Retornar el ArrayList con los tipos de datos de los campos
            return fieldDataTypes;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Retornar null si ocurre un error
        return null;
    }

    @Override
    public ArrayList<String> getTables(String database) {
        return null;
    }
}
