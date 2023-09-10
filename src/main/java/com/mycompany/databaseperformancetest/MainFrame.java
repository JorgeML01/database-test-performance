/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.databaseperformancetest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.DefaultCellEditor;
import javax.swing.DropMode;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Usuario
 */
public class MainFrame extends javax.swing.JFrame implements Events {

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        this.setLayout(new BorderLayout());
        this.setResizable(false);
        this.setTitle("Database Performance Test");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
        this.setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MainTabbedPane = new javax.swing.JTabbedPane();
        MainPane = new javax.swing.JPanel();
        Right = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        Left = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        buttonIniciar = new javax.swing.JButton();
        comboBoxMain = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        PanelTestPerformance = new javax.swing.JPanel();
        panelColor = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        buttonRun = new javax.swing.JButton();
        buttonRegresar = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();
        labelTiempoFinal = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(800, 500));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        MainPane.setBackground(new java.awt.Color(255, 255, 255));
        MainPane.setPreferredSize(new java.awt.Dimension(800, 500));
        MainPane.setLayout(null);

        Right.setBackground(new java.awt.Color(0, 153, 153));
        Right.setPreferredSize(new java.awt.Dimension(400, 500));

        jLabel7.setText("jLabel7");

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/;djfhgslkajbgf.png"))); // NOI18N

        javax.swing.GroupLayout RightLayout = new javax.swing.GroupLayout(Right);
        Right.setLayout(RightLayout);
        RightLayout.setHorizontalGroup(
            RightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RightLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel6)
                .addGap(817, 817, 817)
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(RightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(RightLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel7)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        RightLayout.setVerticalGroup(
            RightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RightLayout.createSequentialGroup()
                .addGroup(RightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(RightLayout.createSequentialGroup()
                        .addGap(136, 136, 136)
                        .addComponent(jLabel5))
                    .addGroup(RightLayout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(57, Short.MAX_VALUE))
            .addGroup(RightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(RightLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel7)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        MainPane.add(Right);
        Right.setBounds(0, 0, 400, 500);

        Left.setBackground(new java.awt.Color(255, 255, 255));
        Left.setMinimumSize(new java.awt.Dimension(400, 500));

        jLabel1.setBackground(new java.awt.Color(0, 153, 153));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 153));
        jLabel1.setText("Elige una Base de datos");

        buttonIniciar.setBackground(new java.awt.Color(0, 153, 153));
        buttonIniciar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        buttonIniciar.setForeground(new java.awt.Color(255, 255, 255));
        buttonIniciar.setText("Iniciar");
        buttonIniciar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonIniciarMouseClicked(evt);
            }
        });

        comboBoxMain.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "MySql", "MariaDB", "PostgreSql", "Firebird", "SqlServer" }));
        comboBoxMain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxMainActionPerformed(evt);
            }
        });

        jLabel2.setBackground(new java.awt.Color(0, 153, 153));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 153, 153));
        jLabel2.setText("ADMIN");

        javax.swing.GroupLayout LeftLayout = new javax.swing.GroupLayout(Left);
        Left.setLayout(LeftLayout);
        LeftLayout.setHorizontalGroup(
            LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LeftLayout.createSequentialGroup()
                .addGroup(LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(LeftLayout.createSequentialGroup()
                        .addGap(145, 145, 145)
                        .addComponent(comboBoxMain, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(LeftLayout.createSequentialGroup()
                        .addGap(138, 138, 138)
                        .addComponent(jLabel2))
                    .addGroup(LeftLayout.createSequentialGroup()
                        .addGap(81, 81, 81)
                        .addComponent(jLabel1))
                    .addGroup(LeftLayout.createSequentialGroup()
                        .addGap(165, 165, 165)
                        .addComponent(buttonIniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(210, Short.MAX_VALUE))
        );
        LeftLayout.setVerticalGroup(
            LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LeftLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel2)
                .addGap(44, 44, 44)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(comboBoxMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(buttonIniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(238, Short.MAX_VALUE))
        );

        MainPane.add(Left);
        Left.setBounds(400, 0, 560, 500);

        MainTabbedPane.addTab("tab2", MainPane);

        PanelTestPerformance.setBackground(new java.awt.Color(255, 255, 255));
        PanelTestPerformance.setPreferredSize(new java.awt.Dimension(800, 500));
        PanelTestPerformance.setLayout(null);

        panelColor.setBackground(new java.awt.Color(0, 102, 102));

        javax.swing.GroupLayout panelColorLayout = new javax.swing.GroupLayout(panelColor);
        panelColor.setLayout(panelColorLayout);
        panelColorLayout.setHorizontalGroup(
            panelColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelColorLayout.createSequentialGroup()
                .addGap(137, 137, 137)
                .addComponent(jLabel3)
                .addContainerGap(663, Short.MAX_VALUE))
        );
        panelColorLayout.setVerticalGroup(
            panelColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelColorLayout.createSequentialGroup()
                .addGap(129, 129, 129)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PanelTestPerformance.add(panelColor);
        panelColor.setBounds(0, 0, 800, 120);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Tablas", "Cantidad", "Truncate"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        PanelTestPerformance.add(jScrollPane1);
        jScrollPane1.setBounds(122, 170, 510, 180);

        buttonRun.setText("Run");
        buttonRun.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonRunMouseClicked(evt);
            }
        });
        PanelTestPerformance.add(buttonRun);
        buttonRun.setBounds(330, 360, 75, 22);

        buttonRegresar.setText("Regresar");
        buttonRegresar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonRegresarMouseClicked(evt);
            }
        });
        PanelTestPerformance.add(buttonRegresar);
        buttonRegresar.setBounds(30, 440, 110, 22);
        PanelTestPerformance.add(jProgressBar1);
        jProgressBar1.setBounds(210, 390, 330, 40);

        labelTiempoFinal.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelTiempoFinal.setForeground(new java.awt.Color(0, 0, 0));
        labelTiempoFinal.setText("Tiempo de inserción: 0.00 segundos");
        PanelTestPerformance.add(labelTiempoFinal);
        labelTiempoFinal.setBounds(280, 140, 240, 16);

        MainTabbedPane.addTab("tab3", PanelTestPerformance);

        getContentPane().add(MainTabbedPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -40, 800, 540));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void comboBoxMainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxMainActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboBoxMainActionPerformed

    private void buttonIniciarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonIniciarMouseClicked
        this.MainTabbedPane.setSelectedIndex(1);
        this.labelTiempoFinal.setText("Tiempo de inserción: 0.00 segundos");

        if (this.comboBoxMain.getSelectedIndex() == 0) {
            System.out.println("MYSQL");
            this.panelColor.setBackground(Color.BLUE);
            db = new MySQLDatabase();
        } else if (this.comboBoxMain.getSelectedIndex() == 1) {
            System.out.println("MARIADB");
            this.panelColor.setBackground(Color.GRAY);
            db = new MariaDBDatabase();
        } else if (this.comboBoxMain.getSelectedIndex() == 2) {
            System.out.println("POSTGRESQL");
            this.panelColor.setBackground(Color.MAGENTA);
            db = new PostgreSQLDatabase();
        } else if (this.comboBoxMain.getSelectedIndex() == 3) {
            System.out.println("FIREBIRD");
            this.panelColor.setBackground(Color.ORANGE);
            db = new FirebirdDatabase();
        } else if (this.comboBoxMain.getSelectedIndex() == 4) {
            System.out.println("SQLServerDatabase");
            this.panelColor.setBackground(Color.DARK_GRAY);
            db = new PostgreSQLDatabase();
        } else {
            System.out.println("NADA");
        }

        db.connect();
        ArrayList<String> tables = db.getTables("EMPRESA");
        setupTable(this.jTable1, tables);

//        db.truncate("EMPLEADO");
//        db.truncate("TELEFONO");
//        db.truncate("DEPARTAMENTO");
//        db.truncate("EMPLEADOS");
//        db.truncate("PRODUCTOS");
        db.disconnect();
    }//GEN-LAST:event_buttonIniciarMouseClicked

    private void buttonRegresarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonRegresarMouseClicked
        this.MainTabbedPane.setSelectedIndex(0);
    }//GEN-LAST:event_buttonRegresarMouseClicked

    private void buttonRunMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonRunMouseClicked
        long tiempoInicial = System.currentTimeMillis();
        this.insertRun(jTable1);
        long tiempoFinal = System.currentTimeMillis();

        // Calcula el tiempo transcurrido en milisegundos
        long tiempoTranscurrido = tiempoFinal - tiempoInicial;
        System.out.println("Tiempo final: " + tiempoFinal);

        // Imprime el tiempo transcurrido en segundos
        double segundosTranscurridos = tiempoTranscurrido / 1000.0;
        System.out.println("Tiempo transcurrido: " + segundosTranscurridos + " segundos");
    }//GEN-LAST:event_buttonRunMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Left;
    private javax.swing.JPanel MainPane;
    private javax.swing.JTabbedPane MainTabbedPane;
    private javax.swing.JPanel PanelTestPerformance;
    private javax.swing.JPanel Right;
    private javax.swing.JButton buttonIniciar;
    private javax.swing.JButton buttonRegresar;
    private javax.swing.JButton buttonRun;
    private javax.swing.JComboBox<String> comboBoxMain;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel labelTiempoFinal;
    private javax.swing.JPanel panelColor;
    // End of variables declaration//GEN-END:variables

    // Extra variables
    private DatabaseManager db;

    // Extra functions.
    public static void setupTable(JTable table, ArrayList<String> arrayList) {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 2 || columnIndex == 3) { // Columnas "Truncate" e "Insertar"
                    return Boolean.class;
                }
                return super.getColumnClass(columnIndex);
            }
        };

        model.addColumn("Tablas");
        model.addColumn("Cantidad");
        model.addColumn("Truncate");
        model.addColumn("Insertar"); // Agregar la columna "Insertar"

        for (String item : arrayList) {
            model.addRow(new Object[]{item, "", false, false}); // Inicializar con casillas desmarcadas
        }

        table.setModel(model);

        // Habilitar la reordenación de filas
        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        TableRowTransferHandler handler = new TableRowTransferHandler();
        table.setTransferHandler(handler);

        // Habilitar el ordenamiento de filas mediante arrastrar y soltar
        table.setDragEnabled(true);
        table.setDropMode(DropMode.INSERT_ROWS);
    }

    private void insertRun(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int rowCount = model.getRowCount();
        String[] tableNames = new String[rowCount];
        int[] cantidadRegistros = new int[rowCount];

        // Recorre las filas del JTable y obtiene los valores de las columnas correspondientes
        for (int row = 0; row < rowCount; row++) {
            String nombreTabla = model.getValueAt(row, 0).toString();
            String cantidadRegistrosStr = model.getValueAt(row, 1).toString();
            String truncateRows = model.getValueAt(row, 2).toString();
            String insertRows = model.getValueAt(row, 3).toString();

            // Truncate de las tables con el checkbox marcado.
            db.connect();
            if (truncateRows.equals("true")) {
                System.out.println("Truncated table: " + nombreTabla);
                db.truncate(nombreTabla);
            }
            db.disconnect();

            // Almacena los valores en los arrays correspondientes en caso de que el checkbox esté marcado
            if (insertRows.equals("true")) {
                tableNames[row] = nombreTabla;
                cantidadRegistros[row] = Integer.parseInt(cantidadRegistrosStr);
            }
        }
        long tiempoInicial = System.currentTimeMillis();
        // Crea una instancia de la clase InsertThread generalizada
        InsertThread insertThread = new InsertThread(jProgressBar1, db, tableNames, cantidadRegistros, tiempoInicial, this);

        // Inicia el hilo
        insertThread.start();

    }

    @Override
    public void InsertThreadListener(double finalTime) {
        //System.out.println("TIEMPO FINAL: " + finalTime);
        this.labelTiempoFinal.setText("Tiempo de inserción: " + finalTime + " segundos");
    }
}
