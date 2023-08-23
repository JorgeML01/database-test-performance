package com.mycompany.databaseperformancetest;

public abstract class DatabaseManager {

    public abstract void insert();

    public abstract void truncate();

    public abstract void connect();

    public abstract void disconnect();
    
    // Vamos a ocupar también obtener lista de tablas.
    // Obtener columnas de tabla.
    
    /**
     * Podríamos tener entonces una variable con el nombre de la tabla.
     * También una lista con cada campo.
     * Una lista con el tipo de dato de cada campo.
     * 
     * Todo eso sería luego de obtenerlo con las funciones de obtener ello.
     * Podríamos entonces hacer que esas funciones retornen el nombre de la tabla
     * y otra función que retorne el tipo de dato en una lista.
     * Por último otra función que retorne los campos de esa tabla.
     * 
     * Así pos [0] de list1 corresponde a pos[0] de list2.
     * 
     * Entonces el insert tendría 3 campos. Nombre de tabla y 2 arraylist.
     */
}
