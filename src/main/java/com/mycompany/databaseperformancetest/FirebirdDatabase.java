package com.mycompany.databaseperformancetest;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.swing.JOptionPane;

public class FirebirdDatabase extends DatabaseManager {

    private Connection connection;

    @Override
    public void insert(String tableName, int cantidadRegistros) {
        connect();
        try {
            ArrayList<String> columnNames = getColumnNames(tableName);
            ArrayList<String> columnDataTypes = getColumnDataType(tableName);
            ArrayList<String> primaryKeyColumns = getPK(tableName);
            ArrayList<String> primaryKeyDataTypes = getPKDataType(tableName); // No sé si realmente voy a ocupar esto porque sería con un select a la otra tb.
            ArrayList<Boolean> isPkIdentity = isPkIdentity(tableName, primaryKeyColumns);
            ArrayList<String> foreignKeyColumns = getFK(tableName);
            ArrayList<String> referencedTables = getTableReference(tableName);
            ArrayList<String> referencedColumns = getColumnReference(tableName);

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

    @Override
    public void truncate(String tableName) {
        try {
            Statement statement = connection.createStatement();
            String query = "DELETE FROM " + tableName;
            statement.executeUpdate(query);
            System.out.println("Truncate exitoso en la tabla: " + tableName);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al truncar la tabla: " + tableName);
        }
    }

    @Override
    public void connect() {
        String url = "jdbc:firebirdsql://localhost:3050/C:\\Firebird\\FirebirdFiles\\_databases\\EMPRESA.FDB";
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
                System.out.println("\n\n\nDesconexión exitosa de Firebird.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al desconectar de la base de datos.");
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
//            if (isPrimaryKey && isPkIdentity.get(primaryKeyColumns.indexOf(columnName))) {
//                continue;
//            }
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
                } else if (dataType.equalsIgnoreCase("BOOLEAN")) {
                    insertQuery.append(generateRandomBool() ? "TRUE" : "FALSE").append(","); // Genera un valor booleano aleatorio
                } else if (dataType.equalsIgnoreCase("DECIMAL")) {
                    insertQuery.append(generateRandomDecimal(0.0, 9999999.0)).append(","); // Genera un valor decimal aleatorio en el rango [0.0, 100.0]
                } else if (dataType.equalsIgnoreCase("INTEGER")) {
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

    public static int generateRandomNumber(int min, int max) {
        try {
            if (min >= max) {
                throw new IllegalArgumentException("El valor mínimo debe ser menor que el valor máximo.");
            }

            Random rand = new Random();
            return rand.nextInt((max - min) + 1) + min;
        } catch (IllegalArgumentException e) {
            String mensaje = "NO SE PUDO HACER EL INSERT!";
            JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
            // Puedes agregar más lógica aquí si es necesario
            
            
            // Sería entonces de enviar una variable en caso de que haya error para hacer truncate de todas las tablas.
            // Porque aunque haya error, siempre se terminan llenando las tablas donde no hubo error.
            
            return 0; // Por ejemplo, puedes devolver un valor predeterminado en caso de error
        }
    }

    public static Map<String, Integer> countTableReferences(ArrayList<String> referencedTables) {
        Map<String, Integer> tableCount = new HashMap<>();

        for (String table : referencedTables) {
            if (!tableCount.containsKey(table)) {
                tableCount.put(table, 0);
            }
        }

        return tableCount;
    }

    public static void quitarEspacios(ArrayList<String> lista) {
        for (int i = 0; i < lista.size(); i++) {
            String elemento = lista.get(i);
            // Reemplaza todos los espacios en blanco con una cadena vacía ""
            elemento = elemento.replaceAll("\\s+", "");
            lista.set(i, elemento);
        }
    }

    public void executeInsertQuery(String insertQuery) throws SQLException {
        PreparedStatement preparedStatement = null;

        try {
            // Crear una declaración preparada para la consulta de inserción
            preparedStatement = connection.prepareStatement(insertQuery);

            // Ejecutar la consulta de inserción
            preparedStatement.executeUpdate();
        } finally {
            // Cerrar la conexión y la declaración preparada
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    // Ver cómo guardar la posicón del registro.
    public String generateForeignKeyValue(String referencedTable, String referencedColumn) throws SQLException {
        String fkValue = "";

        // Consulta para obtener un valor aleatorio de la tabla de referencia.
        String query = "SELECT " + referencedColumn + " FROM " + referencedTable + " ORDER BY RAND() FETCH FIRST 1 ROWS ONLY";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Obtiene un valor aleatorio existente de la tabla de referencia.
                    fkValue = resultSet.getString(referencedColumn);
                } else {
                    // Si no hay valores en la tabla de referencia, puedes manejarlo según tus necesidades.
                    // Por ejemplo, podrías generar un valor aleatorio.
                    fkValue = generateRandomString(10); // Cambia la longitud según tus necesidades.
                }
            }
        }

        return fkValue;
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

    public Map<String, String> getForeignKeyColumns(String tableName, Connection connection) throws SQLException {
        Map<String, String> foreignKeyColumns = new HashMap<>();

        // Consulta para obtener información de clave foránea para la tabla especificada
        String query = "SELECT RDB$FIELD_NAME, RDB$RELATION_NAME, RDB$RELATION_FIELD FROM RDB$REF_CONSTRAINTS "
                + "WHERE RDB$CONST_NAME_UQ LIKE 'INTEG_%' AND RDB$RELATION_NAME = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, tableName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String localColumn = resultSet.getString("RDB$FIELD_NAME");
                    String referencedTable = resultSet.getString("RDB$RELATION_NAME");
                    String referencedColumnName = resultSet.getString("RDB$RELATION_FIELD");

                    // Mapea la columna local con la columna de referencia
                    foreignKeyColumns.put(localColumn, referencedTable + "." + referencedColumnName);
                }
            }
        }

        return foreignKeyColumns;
    }

    public ArrayList<String> getColumnNames(String tableName) {
        ArrayList<String> columnNames = new ArrayList<>();

        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet columns = metaData.getColumns(null, null, tableName, null);

            while (columns.next()) {
                String columnName = columns.getString("COLUMN_NAME");
                columnNames.add(columnName);
            }

            columns.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al obtener los nombres de las columnas de la tabla: " + tableName);
        }

        return columnNames;
    }

    public ArrayList<String> getColumnDataType(String tableName) {
        ArrayList<String> columnDataTypes = new ArrayList<>();

        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet columns = metaData.getColumns(null, null, tableName, null);

            while (columns.next()) {
                String dataType = columns.getString("TYPE_NAME");
                columnDataTypes.add(dataType);
            }

            columns.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al obtener los tipos de datos de las columnas de la tabla: " + tableName);
        }

        return columnDataTypes;
    }

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

    public ArrayList<Boolean> isPkIdentity(String tableName, ArrayList<String> primaryKeyColumns) {
        ArrayList<Boolean> isIdentityList = new ArrayList<>();

        try {
            // Consulta SQL para obtener las columnas autoincrementales
            String sql = "SELECT RDB$FIELD_NAME FROM RDB$RELATION_FIELDS "
                    + "WHERE RDB$RELATION_NAME = ? AND RDB$IDENTITY_TYPE IS NOT NULL";

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
        }

        return isIdentityList;
    }

    public ArrayList<String> getFK(String tableName) {
        ArrayList<String> foreignKeyColumns = new ArrayList<>();

        try {
            // Consulta SQL para obtener las claves foráneas
            String sql = "SELECT RDB$FIELD_NAME FROM RDB$RELATION_CONSTRAINTS RC "
                    + "JOIN RDB$INDEX_SEGMENTS ISG ON RC.RDB$INDEX_NAME = ISG.RDB$INDEX_NAME "
                    + "WHERE RC.RDB$RELATION_NAME = ? AND RC.RDB$CONSTRAINT_TYPE = 'FOREIGN KEY'";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, tableName);
            ResultSet foreignKeys = preparedStatement.executeQuery();

            while (foreignKeys.next()) {
                // Obtener el nombre de la columna que es clave foránea
                String columnName = foreignKeys.getString("RDB$FIELD_NAME");
                foreignKeyColumns.add(columnName);
            }

            foreignKeys.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al obtener las columnas que son claves foráneas en la tabla: " + tableName);
        }

        return foreignKeyColumns;
    }

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

        reverseArrayList(referencedTables);
        return referencedTables;
    }

    // Agregada porque me salían al revés las tablas hacia donde referenciaban.
    public static <T> void reverseArrayList(ArrayList<T> list) {
        int left = 0;
        int right = list.size() - 1;

        while (left < right) {
            // Intercambiar los elementos en las posiciones left y right
            T temp = list.get(left);
            list.set(left, list.get(right));
            list.set(right, temp);

            // Mover los punteros hacia el centro
            left++;
            right--;
        }
    }

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

        reverseArrayList(referencedColumns);
        return referencedColumns;
    }

    public ArrayList<String> getReferencedColumns(String tableName) {
        ArrayList<String> referencedColumns = new ArrayList<>();

        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet exportedKeys = metaData.getExportedKeys(null, null, tableName);

            while (exportedKeys.next()) {
                String columnName = exportedKeys.getString("PKCOLUMN_NAME"); // Cambio en el nombre de la columna
                referencedColumns.add(columnName);
            }

            exportedKeys.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al obtener las columnas a las que apuntan las claves externas en la tabla: " + tableName);
        }

        return referencedColumns;
    }

    @Override
    public ArrayList<String> getTables(String database) {
        ArrayList<String> tableNames = new ArrayList<>();

        try {
            // Obtener el metadatos de la base de datos
            DatabaseMetaData metaData = connection.getMetaData();

            // Obtener el resultado de las tablas en la base de datos
            ResultSet tables = metaData.getTables(null, null, null, new String[]{"TABLE"});

            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                tableNames.add(tableName);
                System.out.println("TABLE NAME: " + tableName);
            }

            tables.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al obtener las tablas de la base de datos: " + e.getMessage());
        }

        return tableNames;
    }

}
