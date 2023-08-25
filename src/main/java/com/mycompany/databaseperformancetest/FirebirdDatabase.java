package com.mycompany.databaseperformancetest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FirebirdDatabase extends DatabaseManager {

    private Connection connection;

    @Override
    public void insert() {

    }

    @Override
    public void truncate() {

    }

    @Override
    public void connect() {
        String url = "jdbc:firebirdsql://localhost:3050/C:\\Firebird\\FirebirdFiles\\_databases\\TEST_DB.FDB";
        String user = "SYSDBA";
        String password = "admin123";

        try {
            // Cargar el controlador JDBC de Firebird
            Class.forName("org.firebirdsql.jdbc.FBDriver");

            // Establecer la conexión
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión exitosa a Firebird.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error al cargar el controlador JDBC.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al conectar a la base de datos.");
        }
    }

    @Override
    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Desconexión exitosa de Firebird.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al desconectar de la base de datos.");
        }
    }

    @Override
    public ArrayList<String> getTableNames() {
        ArrayList<String> tableNames = new ArrayList<>();

        String url = "jdbc:firebirdsql://localhost:3050/C:\\Firebird\\FirebirdFiles\\_databases\\TEST_DB.FDB";
        String user = "SYSDBA";
        String password = "admin123";

        try {
            // Cargar el controlador JDBC de Firebird
            Class.forName("org.firebirdsql.jdbc.FBDriver");

            // Establecer la conexión
            Connection connection = DriverManager.getConnection(url, user, password);

            // Crear una declaración SQL
            Statement statement = connection.createStatement();

            // Ejecutar la consulta
            String query = "SELECT RDB$RELATION_NAME FROM RDB$RELATIONS WHERE RDB$SYSTEM_FLAG = 0 AND RDB$VIEW_BLR IS NULL";
            ResultSet resultSet = statement.executeQuery(query);

            // Procesar los resultados
            while (resultSet.next()) {
                String tableName = resultSet.getString("RDB$RELATION_NAME");
                tableNames.add(tableName);
            }

            // Cerrar recursos
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tableNames;
    }

    @Override
    public ArrayList<String> getTableFields(String tableName) {
        ArrayList<String> fieldNames = new ArrayList<>();

        String url = "jdbc:firebirdsql://localhost:3050/C:\\Firebird\\FirebirdFiles\\_databases\\TEST_DB.FDB";
        String user = "SYSDBA";
        String password = "admin123";

        try {
            // Cargar el controlador JDBC de Firebird
            Class.forName("org.firebirdsql.jdbc.FBDriver");

            // Establecer la conexión
            Connection connection = DriverManager.getConnection(url, user, password);

            // Crear una declaración SQL
            Statement statement = connection.createStatement();

            // Ejecutar la consulta para obtener los campos de la tabla
            String query = "SELECT RDB$FIELD_NAME FROM RDB$RELATION_FIELDS WHERE RDB$RELATION_NAME = '" + tableName + "'";
            ResultSet resultSet = statement.executeQuery(query);

            // Procesar los resultados
            while (resultSet.next()) {
                String fieldName = resultSet.getString("RDB$FIELD_NAME");
                fieldNames.add(fieldName);
            }

            // Cerrar recursos
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fieldNames;
    }

    @Override
    public ArrayList<String> getFieldDataTypes(String tableName) {
        ArrayList<String> fieldInfo = new ArrayList<>();

        String url = "jdbc:firebirdsql://localhost:3050/C:\\Firebird\\FirebirdFiles\\_databases\\TEST_DB.FDB";
        String user = "SYSDBA";
        String password = "admin123";

        try {
            // Cargar el controlador JDBC de Firebird
            Class.forName("org.firebirdsql.jdbc.FBDriver");

            // Establecer la conexión
            Connection connection = DriverManager.getConnection(url, user, password);

            // Crear una declaración SQL
            Statement statement = connection.createStatement();

            // Ejecutar la consulta para obtener la información de los campos
            String query = "SELECT RF.RDB$FIELD_NAME, F.RDB$FIELD_TYPE "
                    + "FROM RDB$RELATION_FIELDS RF "
                    + "JOIN RDB$FIELDS F ON RF.RDB$FIELD_SOURCE = F.RDB$FIELD_NAME "
                    + "WHERE RF.RDB$RELATION_NAME = '" + tableName + "'";
            ResultSet resultSet = statement.executeQuery(query);

            // Procesar los resultados
            while (resultSet.next()) {
                String fieldName = resultSet.getString("RDB$FIELD_NAME");
                int fieldType = resultSet.getInt("RDB$FIELD_TYPE");
                String dataType = getDataTypeName(fieldType);
                String fieldInfoLine = fieldName + "\t" + dataType;
                fieldInfo.add(fieldInfoLine);
            }

            // Cerrar recursos
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fieldInfo;
    }

    public static String getDataTypeName(int fieldType) {
        Map<Integer, String> dataTypeMap = new HashMap<>();
        dataTypeMap.put(7, "SMALLINT");
        dataTypeMap.put(8, "INTEGER");
        dataTypeMap.put(10, "FLOAT");
        dataTypeMap.put(12, "DATE");
        dataTypeMap.put(13, "TIME");
        dataTypeMap.put(14, "CHAR");
        dataTypeMap.put(16, "BIGINT");
        dataTypeMap.put(27, "DOUBLE");
        dataTypeMap.put(35, "TIMESTAMP");
        dataTypeMap.put(37, "VARCHAR");
        // Agregar más tipos según sea necesario

        return dataTypeMap.getOrDefault(fieldType, "DESCONOCIDO");
    }

}
