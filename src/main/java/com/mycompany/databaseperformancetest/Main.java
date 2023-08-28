package com.mycompany.databaseperformancetest;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        
        String tableName = "PEDIDOS";
        FirebirdDatabase db = new FirebirdDatabase();
        db.connect();
        db.insert(tableName);
        //ArrayList<String> tables = db.getTableNames(tableName);
        System.out.println("\n\nTABLE FIELDS");
        ArrayList<String> tableFields = db.getTableFields(tableName);
        imprimirArray(tableFields);
        System.out.println("\n\nDATA TYPES FIELDS");
        ArrayList<String> dataTypesFields = db.getFieldDataTypes(tableName);
        imprimirArray(dataTypesFields);
        System.out.println("\n\n");
        db.disconnect();

    }
    
    public static void imprimirArray(ArrayList<String> list){
        for (int i = 0; i < list.size(); i++){
            System.out.println(list.get(i));
        }
    }
}
