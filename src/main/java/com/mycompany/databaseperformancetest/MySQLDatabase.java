package com.mycompany.databaseperformancetest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class MySQLDatabase extends DatabaseManager {

    @Override
    public void insert() {

    }

    @Override
    public void truncate() {

    }

    @Override
    public void connect() {
        String url = "jdbc:mysql://172.16.165.252:3306/tiendas_betel?useSSL=false&allowPublicKeyRetrieval=true";
        String user = "caja";
        String password = "3434th";

        try {
            // Cargar el controlador JDBC de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establecer la conexión
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión exitosa a MySQL.");

            // Ahora puedes ejecutar consultas y realizar otras operaciones en la base de datos
            // Cerrar la conexión cuando hayas terminado
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error al cargar el controlador JDBC.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al conectar a la base de datos MySQL.");
        }
    }

    @Override
    public void disconnect() {

    }

    @Override
    public ArrayList<String> getTableNames() {

        return null;
    }

    @Override
    public ArrayList<String> getTableFields(String tableName) {
        return null;
    }

    @Override
    public ArrayList<String> getFieldDataTypes(String tableName) {
        return null;
    }
}
