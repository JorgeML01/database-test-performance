/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.databaseperformancetest;

import java.awt.datatransfer.DataFlavor;
import javax.swing.*;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Usuario
 */
public class TableRowTransferHandler extends TransferHandler {

    private int[] indices;
    private int addIndex = -1; // Índice donde se añadirá la fila
    private int addCount = 0; // Número de filas a añadir

    @Override
    protected Transferable createTransferable(JComponent c) {
        JTable table = (JTable) c;
        indices = table.getSelectedRows();
        return new StringSelection(""); // No necesitamos transferir datos reales
    }

    @Override
    public int getSourceActions(JComponent c) {
        return TransferHandler.MOVE; // Solo permitir mover, no copiar
    }

    @Override
    public boolean canImport(TransferHandler.TransferSupport support) {
        return support.isDataFlavorSupported(DataFlavor.stringFlavor);
    }

    @Override
    public boolean importData(TransferHandler.TransferSupport support) {
        if (!canImport(support)) {
            return false;
        }   

        JTable table = (JTable) support.getComponent();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        JTable.DropLocation dl = (JTable.DropLocation) support.getDropLocation();

        int dropRow = dl.getRow();

        // Calcular dónde se debe insertar la fila
        if (dl.isInsertRow()) {
            addIndex = dropRow;
        } else {
            addIndex = dropRow < indices[0] ? dropRow : dropRow - 1;
        }

        addCount = indices.length;

        // Mover las filas
        for (int i = 0; i < indices.length; i++) {
            int fromIndex = indices[i];
            if (fromIndex < addIndex) {
                addIndex--;
            }
            model.moveRow(fromIndex, fromIndex, addIndex++);
        }

        return true;
    }

}
