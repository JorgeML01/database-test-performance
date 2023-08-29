package com.mycompany.databaseperformancetest;

public abstract class DatabaseManager {

    public abstract void insert(String tableName, int cantidadRegistros);

    public abstract void truncate(String tableName);

    public abstract void connect();

    public abstract void disconnect();
    
}
