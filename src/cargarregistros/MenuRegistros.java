package cargarregistros;


import monitor.Registro;
import monitor.Registros;
import monitor.Sintoma;
import monitor.Sintomas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MenuRegistros extends JFrame implements ItemListener, ActionListener {
    private final JButton registrar;
//    private List<JCheckBox> opciones;

    Sintomas sintomasMonitor;
    Sintomas sintomasUsuario;
    Registros registros;
    private JPanel contentPane;
    private JPanel panel;

    private JPanel panel_1;
    private List<JCheckBoxSintoma> checkboxes = new ArrayList<JCheckBoxSintoma>();

    JTable table;
    JScrollPane tableScollPanel;
    DefaultTableModel dataTable;
    final MenuRegistros menuRegistros = this;

    public MenuRegistros(Sintomas sintomasMonitor, Registros registros, Sintomas sintomasUsuario){
        this.sintomasMonitor = sintomasMonitor;
        this.sintomasUsuario = sintomasUsuario;
        this.registros = registros;

        ArchivoRegistros archivoRegistros = new ArchivoRegistros();

        setBounds(120,120,800,700);

//        setLayout(null);
        setTitle("Registros");
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel_1 = new JPanel();
        registrar = new JButton("Registrar");
//        add(registrar);
        registrar.addActionListener(this);
        registrar.setBounds(0, 50, 90,  30);
        panel_1.add(registrar);



        for(Sintoma s: sintomasMonitor) {
            JCheckBoxSintoma checkBox = new JCheckBoxSintoma(s);
            checkBox.setText(s.toString());
            panel.add(checkBox);
            checkboxes.add(checkBox);
            panel.validate();
            panel.repaint();
        }

        dataTable = new DefaultTableModel();
        table = new JTable(dataTable);
        tableScollPanel = new JScrollPane(table);
        dataTable.addColumn("Fecha");
        dataTable.addColumn("Sintomas");

        for(Registro r: registros){
            SimpleDateFormat format = new SimpleDateFormat("HH:mm - dd-MM-yyyy");
            String fechaRegistro = format.format(r.getFecha());
            String sintomasRegistro = "";
            for(Sintoma s: r.getSintomas()){
                sintomasRegistro += s.toString()+"-";
            }
            dataTable.addRow(new Object[]{fechaRegistro, sintomasRegistro });
        }
        tableScollPanel.setBounds(100, 10, 100,80);
        panel.add(tableScollPanel);
        contentPane.add(panel, BorderLayout.CENTER);
        contentPane.add(panel_1, BorderLayout.SOUTH);


        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent we){
            try {
                archivoRegistros.guardarRegistros(registros);

            } catch (IOException e) {
                e.printStackTrace();
            }

            synchronized(menuRegistros){
            menuRegistros.notify();
            }
            menuRegistros.setVisible(false);
            menuRegistros.dispose();
            }
        });
        setVisible(true);
        synchronized(this){
            try{
                this.wait();
            }
            catch(InterruptedException ex){
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object botonPulsado = e.getSource();
        if (botonPulsado == registrar) {
            for(JCheckBoxSintoma j: checkboxes) {
                if(j.isSelected()){
                    sintomasUsuario.add(j.getSintoma());
                }
            }
            this.registros.push(new Registro(new Date(), sintomasUsuario));

            try {
                ArchivoRegistros archivoRegistros = new ArchivoRegistros();
                archivoRegistros.guardarRegistros(registros);

            } catch (IOException ex) {
                ex.printStackTrace();
            }

            synchronized(menuRegistros){
                menuRegistros.notify();
            }
            menuRegistros.setVisible(false);
            menuRegistros.dispose();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }
}
