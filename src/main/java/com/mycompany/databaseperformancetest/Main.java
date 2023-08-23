package com.mycompany.databaseperformancetest;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        DatabaseManager database = null;

        System.out.println("Escoja la base de datos con la que trabajará: ");
        String databaseSeleccionada = sc.nextLine();

        if (databaseSeleccionada.equals("MariaDB")) {
            database = new MariaDBDatabase();
        } else if (databaseSeleccionada.equals("PostgreSQL")) {
            database = new PostgreSQLDatabase();
        } else if (databaseSeleccionada.equals("Firebird")) {
            database = new FirebirdDatabase();
        } else if (databaseSeleccionada.equals("MySQL")) {
            database = new MySQLDatabase();
        } else if (databaseSeleccionada.equals("SQL Server")) {
            database = new SQLServerDatabase();
        }

        
        database.connect();
    }
}
