package com.mycompany.databaseperformancetest;

import static com.mycompany.databaseperformancetest.FirebirdDatabase.countTableReferences;
import static com.mycompany.databaseperformancetest.FirebirdDatabase.generateRandomNumber;
import static com.mycompany.databaseperformancetest.FirebirdDatabase.quitarEspacios;
import static com.mycompany.databaseperformancetest.FirebirdDatabase.reverseArrayList;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class MySQLDatabase extends DatabaseManager {

    private Connection connection;

    @Override
    public void truncate(String tableName) {
        try {
            Statement statement = connection.createStatement();

            String truncateQuery = "TRUNCATE TABLE " + tableName;
            statement.executeUpdate(truncateQuery);

            System.out.println("Table " + tableName + " truncated successfully.");

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void connect() {
        String jdbcUrl = "jdbc:mysql://26.210.205.167:3306/empresa?useSSL=false&allowPublicKeyRetrieval=true";
        String username = "Jorge";
        String password = "12345";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(jdbcUrl, username, password);

            System.out.println("Connected to the database!");

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

    @Override
    public void insert(String tableName, int cantidadRegistros) {
        connect();
        try {
            /*#1*/ ArrayList<String> columnNames = getColumnNames(tableName);
            /*#2*/ ArrayList<String> columnDataTypes = getColumnDataType(tableName);
            /*#3*/ ArrayList<String> primaryKeyColumns = getPK(tableName);
            /*#4*/ ArrayList<String> primaryKeyDataTypes = getPKDataType(tableName); // No sé si realmente voy a ocupar esto porque sería con un select a la otra tb.
            /*#5*/ ArrayList<Boolean> isPkIdentity = isPkIdentity(tableName, primaryKeyColumns);
            /*#6*/ ArrayList<String> foreignKeyColumns = getFK(tableName);
            /*#7*/ ArrayList<String> referencedTables = getTableReference(tableName);
            /*#8*/ ArrayList<String> referencedColumns = getColumnReference(tableName);

            for (int i = 0; i < cantidadRegistros; i++) {
                //Genera un registro aleatorio y realiza la inserción
                insertRecord(tableName, columnNames, columnDataTypes, primaryKeyColumns, primaryKeyDataTypes, isPkIdentity,
                        foreignKeyColumns, referencedTables, referencedColumns);
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            System.err.println("Error al insertar registros en la tabla: " + tableName);
        } finally {
            disconnect();
        }
    }

    public void insertRecord(String tableName, ArrayList<String> columnNames, ArrayList<String> columnDataTypes,
            ArrayList<String> primaryKeyColumns, ArrayList<String> primaryKeyDataTypes,
            ArrayList<Boolean> isPkIdentity, ArrayList<String> foreignKeyColumns,
            ArrayList<String> referencedTables, ArrayList<String> referencedColumns) throws SQLException {
        StringBuilder insertQuery = new StringBuilder("INSERT INTO " + tableName + " (");

        // AQUÍ ESTOY EDITANDO. CUANDO CAMBIE DE TABLA ACTUAL A UNA NUEVA, SE PASA A 0 EL INDEX.
        boolean cambio = true;
        String actual = "";
        int posIndex = 0;
        int posicionFK = 0;
        boolean primerReg = true;
        int randomNumber = 0;

        // Agrega la lista de columnas de destino
        for (String columnName : columnNames) {
            insertQuery.append(columnName).append(",");
        }

        // Elimina la última coma
        insertQuery.setLength(insertQuery.length() - 1);

        insertQuery.append(") VALUES (");

        // TEST
        Map<String, Integer> positionMapFK = countTableReferences(referencedTables);
        System.out.println("Map");
        for (Map.Entry<String, Integer> entry : positionMapFK.entrySet()) {
            String tableNames = entry.getKey();
            int count = entry.getValue();
            System.out.println("Tabla: " + tableNames + ", Cantidad de repeticiones: " + count);
        }

        System.out.println(referencedTables.size());
        for (int i = 0; i < referencedTables.size(); i++) {
            System.out.println("TABLE: " + referencedTables.get(i));
        }
        //

        quitarEspacios(foreignKeyColumns);
        System.out.println("\n\nForeign key columns");
        for (int j = 0; j < foreignKeyColumns.size(); j++) {
            System.out.println(foreignKeyColumns.get(j));
        }
        System.out.println("\n\n");

        for (int i = 0; i < columnDataTypes.size(); i++) {

            System.out.println("\n\n\n" + insertQuery + "\n\n\n");

            String dataType = columnDataTypes.get(i);
            String columnName = columnNames.get(i);

            // Verifica si la columna es parte de la clave primaria compuesta
            boolean isPrimaryKey = primaryKeyColumns.contains(columnName);

            // Si es una columna autoincremental, omítela en la inserción
            /*if (isPrimaryKey && isPkIdentity.get(primaryKeyColumns.indexOf(columnName))) {
                continue;
            }*/
            System.out.println("ColumnName: " + columnName);

            if (foreignKeyColumns.contains(columnName)) {

                try {
                    int index = foreignKeyColumns.indexOf(columnName);
                    String referencedTable = referencedTables.get(index);

                    ArrayList pkFK = getPK(referencedTable);
                    ArrayList pkFKDataType = getPKDataType(referencedTable);

                    if (primerReg) {
                        randomNumber = generateRandomNumber(1, getTableRowCount(referencedTable));
                        primerReg = false;
                    }
                    if (!referencedTable.equals(actual)) {
                        actual = referencedTable;
                        posIndex = 0;
                        posicionFK = 0;
                        randomNumber = generateRandomNumber(1, getTableRowCount(referencedTable));
                    }
                    System.out.println("\n\nRANDOM NUMBER: " + randomNumber);
                    // Porque salen al revés al parecer. TEST.
                    reverseArrayList(pkFK);
                    reverseArrayList(pkFKDataType);

                    // Aquí es donde tendría que hacer esa modificación.
                    int position = positionMapFK.getOrDefault(referencedTable, -1);
                    if (position >= 0 && position < pkFK.size()) {
                        String referencedColumn = (String) pkFK.get(position);
                        System.out.println(referencedColumn + " ---------------");

                        //
                        ArrayList<String> values = getColumnValues(referencedTable, referencedColumn);

                        System.out.println("ARRAY DATA");
                        System.out.println("NUMBER: " + randomNumber);
                        for (int p = 0; p < values.size(); p++) {
                            System.out.println("Valor: " + values.get(p));
                        }

                        //
                        // Genera un valor válido basado en la tabla de referencia.
                        String fkValue = "";
                        if (pkFKDataType.get(posIndex).equals("INTEGER")) {
                            // Usar el valor de values.get(randomNumber) para fkValue
                            System.out.println("random ins: " + randomNumber);

                            // Ver cómo manejar para que sea random y no necesariamente 0.
                            fkValue = values.get(0);

                            // Agregar el valor al query
                            insertQuery.append(fkValue).append(",");
                        } else {
                            // Generar un nuevo valor para fkValue
                            System.out.println("random ins: " + randomNumber);

                            // Ver cómo manejar para que sea random y no necesariamente 0.
                            fkValue = values.get(0);

                            // Agregar el valor al query
                            insertQuery.append("'").append(fkValue).append("',");
                        }

                        //insertQuery.append(fkValue).append(",");
                        System.out.println("fkValue: " + fkValue);

                        // Actualiza el contador en el mapa
                        positionMapFK.put(referencedTable, position + 1);
                        posIndex++;
                    }
                } catch (IndexOutOfBoundsException e) {
                    // Manejar la excepción de índice fuera de rango aquí
                    e.printStackTrace();
                }

            } else {
                // La columna no es una clave externa (FK), por lo que debes generar un valor aleatorio basado en el tipo de dato.
                if (dataType.equalsIgnoreCase("VARCHAR")) {
                    insertQuery.append("'").append(generateRandomString(10)).append("',"); // Genera una cadena aleatoria de longitud 10
                } else if (dataType.equalsIgnoreCase("BIT")) {
                    insertQuery.append(generateRandomBool() ? "TRUE" : "FALSE").append(","); // Genera un valor booleano aleatorio
                } else if (dataType.equalsIgnoreCase("DECIMAL")) {
                    insertQuery.append(generateRandomDecimal(0.0, 9999999.0)).append(","); // Genera un valor decimal aleatorio en el rango [0.0, 100.0]
                } else if (dataType.equalsIgnoreCase("INT")) {
                    insertQuery.append(generateRandomInt(1, 9999999)).append(","); // Genera un valor entero aleatorio en el rango [1, 100]
                } else {
                    // El tipo de datos no es uno de los soportados, dejar como NULL
                    insertQuery.append("NULL,");
                }
            }
        }

        // Elimina la última coma
        insertQuery.setLength(insertQuery.length() - 1);

        insertQuery.append(")");

        System.out.println("\n\n" + insertQuery + "\n\n");
        // Ejecutar la consulta de inserción en la base de datos
        executeInsertQuery(insertQuery.toString());
    }

    //PASOS PARA EL INSERT:
    //1. OBTENER METADATA
    
    /*#1*/
    public ArrayList<String> getColumnNames(String tableName) {
        ArrayList<String> columnNames = new ArrayList<>();

        try {
            DatabaseMetaData metaData = connection.getMetaData();

            ResultSet resultSet = metaData.getColumns(null, null, tableName, null);

            while (resultSet.next()) {
                String columnName = resultSet.getString("COLUMN_NAME");
                columnNames.add(columnName);
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al obtener los nombres de las columnas de la tabla: " + tableName);
        }

        return columnNames;
    }

    /*#2*/
    public ArrayList<String> getColumnDataType(String tableName) {
        ArrayList<String> columnDataTypes = new ArrayList<>();

        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getColumns(null, null, tableName, null);

            // Imprimir los nombres y tipos de datos de las columnas
            while (resultSet.next()) {
                String dataType = resultSet.getString("TYPE_NAME");
                columnDataTypes.add(dataType);
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al obtener los tipos de datos de las columnas de la tabla: " + tableName);
        }

        return columnDataTypes;
    }

    /*#3*/
    public ArrayList<String> getPK(String tableName) {
        ArrayList<String> primaryKeyColumns = new ArrayList<>();

        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet primaryKeys = metaData.getPrimaryKeys(null, null, tableName);

            while (primaryKeys.next()) {
                String columnName = primaryKeys.getString("COLUMN_NAME");
                primaryKeyColumns.add(columnName);
            }

            primaryKeys.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al obtener las columnas de clave primaria de la tabla: " + tableName);
        }

        return primaryKeyColumns;
    }

    /*#4*/
    public ArrayList<String> getPKDataType(String tableName) {
        ArrayList<String> primaryKeyDataTypes = new ArrayList<>();

        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet primaryKeys = metaData.getPrimaryKeys(null, null, tableName);

            while (primaryKeys.next()) {
                String columnName = primaryKeys.getString("COLUMN_NAME");
                String dataType = getColumnDataType(tableName, columnName);
                primaryKeyDataTypes.add(dataType);
            }

            primaryKeys.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al obtener los tipos de datos de las columnas de clave primaria de la tabla: " + tableName);
        }

        return primaryKeyDataTypes;
    }

    /*#4.1*/
    public String getColumnDataType(String tableName, String columnName) {
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet columns = metaData.getColumns(null, null, tableName, columnName);

            if (columns.next()) {
                String dataType = columns.getString("TYPE_NAME");
                columns.close();
                return dataType;
            }

            columns.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al obtener el tipo de datos de la columna: " + columnName);
        }

        return null;
    }
    
    /*#5*/
    public ArrayList<Boolean> isPkIdentity(String tableName, ArrayList<String> primaryKeyColumns) {
        ArrayList<Boolean> isIdentityList = new ArrayList<>();

        /*try {
            // Consulta SQL para obtener las columnas autoincrementales
            String sql = "SELECT COLUMN_NAME " +
                     "FROM INFORMATION_SCHEMA.COLUMNS " +
                     "WHERE TABLE_NAME = ? AND EXTRA LIKE 'auto_increment'";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, tableName);
            ResultSet autoIncrementColumns = preparedStatement.executeQuery();

            // Construir un conjunto de nombres de columnas autoincrementales
            Set<String> autoIncrementColumnNames = new HashSet<>();
            while (autoIncrementColumns.next()) {
                String columnName = autoIncrementColumns.getString("RDB$FIELD_NAME");
                autoIncrementColumnNames.add(columnName);
            }
            autoIncrementColumns.close();

            // Verificar si las columnas de clave primaria son de identidad
            for (String columnName : primaryKeyColumns) {
                boolean isIdentity = autoIncrementColumnNames.contains(columnName);
                isIdentityList.add(isIdentity);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al determinar si las columnas de clave primaria son de identidad en la tabla: " + tableName);
        }*/

        return isIdentityList;
    }
    
    /*#6*/
    public ArrayList<String> getFK(String tableName) {
        ArrayList<String> foreignKeyColumns = new ArrayList<>();

        try {
            DatabaseMetaData metaData = connection.getMetaData();

            // Obtener la información de las claves foráneas
            ResultSet foreignKeys = metaData.getImportedKeys(connection.getCatalog(), null, tableName);

            while (foreignKeys.next()) {
                // Obtener el nombre de la columna que es clave foránea
                String columnName = foreignKeys.getString("FKCOLUMN_NAME");
                foreignKeyColumns.add(columnName);
            }

            foreignKeys.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al obtener las columnas que son claves foráneas en la tabla: " + tableName);
        }

        return foreignKeyColumns;
    }

    /*#7*/
    public ArrayList<String> getTableReference(String tableName) {
        ArrayList<String> referencedTables = new ArrayList<>();

        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet foreignKeys = metaData.getImportedKeys(null, null, tableName);

            while (foreignKeys.next()) {
                String referencedTable = foreignKeys.getString("PKTABLE_NAME");
                referencedTables.add(referencedTable);
            }

            foreignKeys.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al obtener las tablas de referencia para la tabla: " + tableName);
        }

        return referencedTables;
    }
    
    /*#8*/
    public ArrayList<String> getColumnReference(String tableName) {
        ArrayList<String> referencedColumns = new ArrayList<>();

        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet importedKeys = metaData.getImportedKeys(null, null, tableName);

            while (importedKeys.next()) {
                String columnName = importedKeys.getString("FKCOLUMN_NAME");
                referencedColumns.add(columnName);
            }

            importedKeys.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al obtener las columnas que hacen referencia en la tabla: " + tableName);
        }

        return referencedColumns;
    }

    public int getTableRowCount(String tableName) throws SQLException {
        int rowCount = 0;

        // Consulta para contar las filas en la tabla
        String query = "SELECT COUNT(*) FROM " + tableName;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    rowCount = resultSet.getInt(1); // El índice 1 representa la primera columna en el resultado
                }
            }
        }

        return rowCount;
    }

    public void executeInsertQuery(String insertQuery) throws SQLException {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.executeUpdate();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    public ArrayList<String> getColumnValues(String tableName, String columnName) throws SQLException {
        ArrayList<String> columnValues = new ArrayList<>();

        String query = "SELECT " + columnName + " FROM " + tableName;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query); ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String columnValue = resultSet.getString(columnName);
                columnValues.add(columnValue);
            }
        }

        return columnValues;
    }

    //PASO 2: GENERAR LOS RANDOMS
    // Genera una cadena aleatoria de longitud dada
    public String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder randomString = new StringBuilder(length);

        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            randomString.append(characters.charAt(randomIndex));
        }

        return randomString.toString();
    }

    // Genera un valor booleano aleatorio (true o false)
    public boolean generateRandomBool() {
        Random random = new Random();
        return random.nextBoolean();
    }

    // Genera un valor decimal aleatorio en un rango dado
    public double generateRandomDecimal(double minValue, double maxValue) {
        Random random = new Random();
        return minValue + (maxValue - minValue) * random.nextDouble();
    }

    // Genera un valor entero aleatorio en un rango dado
    public int generateRandomInt(int minValue, int maxValue) {
        Random random = new Random();
        return random.nextInt((maxValue - minValue) + 1) + minValue;
    }

    // FUNCIÓN PARA MOSTRAR LAS TABLAS EN FRONTEND
    @Override
    public ArrayList<String> getTables(String database) {
        ArrayList<String> nombresTablas = new ArrayList<>();

        try {

            Statement sentencia = connection.createStatement();
            String consulta = "SHOW TABLES IN " + database;
            ResultSet resultado = sentencia.executeQuery(consulta);

            // Iterar a través del resultado para obtener los nombres de las tablas
            while (resultado.next()) {
                String nombreTabla = resultado.getString(1);
                nombresTablas.add(nombreTabla);
            }

            // Cerrar recursos
            resultado.close();
            sentencia.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nombresTablas;
    }

}
