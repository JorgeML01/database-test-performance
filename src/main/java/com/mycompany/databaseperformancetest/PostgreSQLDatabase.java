package com.mycompany.databaseperformancetest;

import java.util.ArrayList;

public class PostgreSQLDatabase extends DatabaseManager {

    @Override
    public void insert(String tableName, int cantidadRegistros) {

    }

    @Override
    public void truncate() {

    }

    @Override
    public void connect() {

    }

    @Override
    public void disconnect() {

    }

    public ArrayList<String> getTableNames() {

        return null;
    }

    public ArrayList<String> getTableFields(String tableName) {
        return null;
    }

    public ArrayList<String> getFieldDataTypes(String tableName) {
        return null;
    }
}
