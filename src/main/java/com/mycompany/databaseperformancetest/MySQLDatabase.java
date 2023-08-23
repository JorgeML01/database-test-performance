package com.mycompany.databaseperformancetest;

public class MySQLDatabase extends DatabaseManager {

    @Override
    public void insert() {

    }

    @Override
    public void truncate() {

    }

    @Override
    public void connect() {
        System.out.println("SE HA CONECTADO A MYSQL.");
    }

    @Override
    public void disconnect() {

    }
}
