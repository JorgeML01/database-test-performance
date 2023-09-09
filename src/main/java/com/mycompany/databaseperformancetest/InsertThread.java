/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.databaseperformancetest;

import javax.swing.JProgressBar;

public class InsertThread extends Thread {

    private JProgressBar progressBar;
    private DatabaseManager db;
    private String[] tableNames;
    private int[] cantidadRegistros;
    private long tiempoInicial; // Nueva variable
    private Events listener;

    public InsertThread(JProgressBar progressBar, DatabaseManager db, String[] tableNames, int[] cantidadRegistros, long tiempoInicial, Events listener) {
        this.progressBar = progressBar;
        this.db = db;
        this.tableNames = tableNames;
        this.cantidadRegistros = cantidadRegistros;
        this.tiempoInicial = tiempoInicial; // Inicializa la variable tiempoInicial
        this.listener = listener;
    }

    @Override
    public void run() {
        int totalRegistros = calcularTotalRegistros(); // Calcula el total de registros en todas las tablas
        int progresoTotal = 0; // Inicializa el progreso total

        // Ciclo para recorrer todas las tablas
        for (int tableIndex = 0; tableIndex < tableNames.length; tableIndex++) {
            String tableName = tableNames[tableIndex];
            int registros = cantidadRegistros[tableIndex];

            // Realiza las inserciones en un bucle
            for (int i = 0; i < registros; i++) {
                // Realiza la inserción de un registro
                db.insert(tableName, 1); // Inserta un registro a la vez, ajusta según sea necesario

                // Incrementa el progreso total
                progresoTotal++;

                // Calcula el progreso en porcentaje
                int porcentaje = (int) ((progresoTotal / (double) totalRegistros) * 100);

                // Actualiza la barra de progreso
                progressBar.setValue(porcentaje);

                // Agrega una pausa para simular el tiempo que lleva la inserción
                try {
                    Thread.sleep(100); // Puedes ajustar el tiempo de pausa según sea necesario
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                    System.out.println("HOLA");
                }
            }
        }

        // No necesitas establecer el valor máximo de la barra de progreso aquí
        // Calcula el tiempo transcurrido al finalizar el hilo
        long tiempoFinal = System.currentTimeMillis();
        long tiempoTranscurrido = tiempoFinal - tiempoInicial;
        System.out.println("Tiempo transcurrido: " + tiempoTranscurrido + " milisegundos");

        double segundosTranscurridos = tiempoTranscurrido / 1000.0;
        System.out.println("Tiempo transcurrido: " + segundosTranscurridos + " segundos");

        this.listener.InsertThreadListener(segundosTranscurridos);
        this.progressBar.setValue(0);
    }

    private int calcularTotalRegistros() {
        int total = 0;
        for (int registros : cantidadRegistros) {
            total += registros;
        }
        return total;
    }

}
