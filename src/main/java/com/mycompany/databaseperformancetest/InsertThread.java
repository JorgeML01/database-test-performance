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
    private long tiempoInicial;
    private Events listener;
    private int blockSize;

    public InsertThread(JProgressBar progressBar, DatabaseManager db, String[] tableNames, int[] cantidadRegistros, long tiempoInicial, Events listener, int blockSize) {
        this.progressBar = progressBar;
        this.db = db;
        this.tableNames = tableNames;
        this.cantidadRegistros = cantidadRegistros;
        this.tiempoInicial = tiempoInicial;
        this.listener = listener;
        this.blockSize = blockSize;
    }

    @Override
    public void run() {
        int totalRegistros = calcularTotalRegistros(); // Calcula el total de registros en todas las tablas
        int progresoTotal = 0; // Inicializa el progreso total
        long tiempoAcumulado = 0; // Inicializa el tiempo acumulado

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

                // Calcula el tiempo transcurrido
                long tiempoActual = System.currentTimeMillis();
                long tiempoTranscurrido = tiempoActual - tiempoInicial;

                // Agrega el tiempo transcurrido al tiempo acumulado
                tiempoAcumulado += tiempoTranscurrido;

                // Notifica al listener con el tiempo acumulado
                double segundosTranscurridos = tiempoAcumulado / 1000.0;
                this.listener.InsertThreadListener(segundosTranscurridos);

                // Actualiza el tiempo inicial para el siguiente ciclo
                tiempoInicial = tiempoActual;

                // Agrega una pausa para simular el tiempo que lleva la inserción
                try {
                    Thread.sleep(100); // Puedes ajustar el tiempo de pausa según sea necesario
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        // Calcula el tiempo transcurrido al finalizar el hilo
        long tiempoFinal = System.currentTimeMillis();
        long tiempoTranscurrido = tiempoFinal - tiempoInicial;
        System.out.println("Tiempo transcurrido: " + tiempoTranscurrido + " milisegundos");

        double segundosTranscurridos = tiempoAcumulado / 1000.0;
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
