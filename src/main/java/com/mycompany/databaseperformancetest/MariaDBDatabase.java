package com.mycompany.databaseperformancetest;

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
}
