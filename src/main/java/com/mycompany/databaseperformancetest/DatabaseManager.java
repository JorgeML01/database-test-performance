package com.mycompany.databaseperformancetest;

import java.util.ArrayList;

public abstract class DatabaseManager {

    public abstract void insert();

    public abstract void truncate();

    public abstract void connect();

    public abstract void disconnect();
    
    public abstract ArrayList<String> getTableNames();
    
    public abstract ArrayList<String> getTableFields(String tableName);
    
    public abstract ArrayList<String> getFieldDataTypes(String tableName);
    
}
