package com.mycompany.databaseperformancetest;

public abstract class DatabaseManager {

    public abstract void insert(String tableName, int cantidadRegistros);

    public abstract void truncate();

    public abstract void connect();

    public abstract void disconnect();
    
}
