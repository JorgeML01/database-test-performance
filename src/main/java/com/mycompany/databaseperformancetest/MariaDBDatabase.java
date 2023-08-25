package com.mycompany.databaseperformancetest;

import java.util.ArrayList;

public class MariaDBDatabase extends DatabaseManager {

    @Override
    public void insert() {

    }

    @Override
    public void truncate() {

    }

    @Override
    public void connect() {
        System.out.println("SE HA CONECTADO A MARIADB.");
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
