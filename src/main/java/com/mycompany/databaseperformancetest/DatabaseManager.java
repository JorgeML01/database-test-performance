package com.mycompany.databaseperformancetest;

import java.util.ArrayList;

public abstract class DatabaseManager {

    public abstract void insert(String tableName, int cantidadRegistros);

    public abstract void truncate(String tableName);

    public abstract void connect();

    public abstract void disconnect();
    
    public abstract ArrayList<String> getTables(String database);
    
}
