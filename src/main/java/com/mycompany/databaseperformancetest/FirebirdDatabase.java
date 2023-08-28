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

    public void insert(String tableName) {
        System.out.println("\nLlaves foráneas: ");
        this.listForeignKeys(tableName);
        System.out.println("\nLlaves primarias: ");
        ArrayList<String> primaryKeyInfo = getPrimaryKeys(tableName);
        for (String info : primaryKeyInfo) {
            System.out.println(info);
        }
        System.out.println("\n\n");
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
            System.out.println("\n\n\n\nConexión exitosa a Firebird.");

        } catch (ClassNotFoundException e) {
            //e.printStackTrace();
            System.err.println("Error al cargar el controlador JDBC.");
        } catch (SQLException e) {
            //e.printStackTrace();
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
            //e.printStackTrace();
            System.out.println();
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

    //TESTING
    //
    //
    //
    public void listForeignKeys(String tableName) {
        try {
            if (connection == null) {
                System.err.println("La conexión es nula. Asegúrate de haber llamado a connect() antes de listar las llaves foráneas.");
                this.connect();
            }

            String query = "SELECT "
                    + "    RC.RDB$CONSTRAINT_NAME AS FK_CONSTRAINT_NAME, "
                    + "    I.RDB$FIELD_NAME AS FK_COLUMN_NAME, "
                    + "    RI.RDB$RELATION_NAME AS REFERENCED_TABLE_NAME, "
                    + "    SEG.RDB$FIELD_NAME AS REFERENCED_COLUMN_NAME "
                    + "FROM "
                    + "    RDB$RELATION_CONSTRAINTS RC "
                    + "    JOIN RDB$INDEX_SEGMENTS I ON RC.RDB$INDEX_NAME = I.RDB$INDEX_NAME "
                    + "    JOIN RDB$INDEX_SEGMENTS SEG ON RC.RDB$INDEX_NAME = SEG.RDB$INDEX_NAME AND I.RDB$FIELD_POSITION = SEG.RDB$FIELD_POSITION "
                    + "    JOIN RDB$REF_CONSTRAINTS RC2 ON RC2.RDB$CONSTRAINT_NAME = RC.RDB$CONSTRAINT_NAME "
                    + "    JOIN RDB$RELATION_CONSTRAINTS RI ON RC2.RDB$CONST_NAME_UQ = RI.RDB$CONSTRAINT_NAME "
                    + "WHERE "
                    + "    RC.RDB$CONSTRAINT_TYPE = 'FOREIGN KEY' "
                    + "    AND RC.RDB$RELATION_NAME = '" + tableName + "'";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String fkConstraintName = resultSet.getString("FK_CONSTRAINT_NAME");
                String fkColumnName = resultSet.getString("FK_COLUMN_NAME");
                String referencedTableName = resultSet.getString("REFERENCED_TABLE_NAME");
                String referencedColumnName = resultSet.getString("REFERENCED_COLUMN_NAME");

                System.out.println("\nForeign Key Constraint: " + fkConstraintName);
                System.out.println("Foreign Key Column: " + fkColumnName);
                System.out.println("Referenced Table: " + referencedTableName);
                System.out.println("Referenced Column: " + referencedColumnName);
                System.out.println("\n");
            }

            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getPrimaryKeys(String tableName) {
        ArrayList<String> primaryKeyInfo = new ArrayList<>();

        try {
            String query = "SELECT RF.RDB$FIELD_NAME, F.RDB$FIELD_TYPE "
                    + "FROM RDB$RELATION_FIELDS RF "
                    + "JOIN RDB$FIELDS F ON RF.RDB$FIELD_SOURCE = F.RDB$FIELD_NAME "
                    + "WHERE RF.RDB$RELATION_NAME = '" + tableName + "' "
                    + "AND RF.RDB$FIELD_NAME IN (SELECT S.RDB$FIELD_NAME FROM RDB$INDEX_SEGMENTS S WHERE S.RDB$INDEX_NAME = (SELECT I.RDB$INDEX_NAME FROM RDB$INDICES I WHERE I.RDB$RELATION_NAME = '" + tableName + "' AND I.RDB$UNIQUE_FLAG = 1))";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String fieldName = resultSet.getString("RDB$FIELD_NAME");
                int fieldType = resultSet.getInt("RDB$FIELD_TYPE");
                String dataType = getDataTypeName(fieldType);
                String primaryKeyInfoLine = fieldName + "\t" + dataType;
                primaryKeyInfo.add(primaryKeyInfoLine);
            }

            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return primaryKeyInfo;
    }

}
