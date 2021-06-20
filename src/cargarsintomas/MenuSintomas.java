package cargarsintomas;

import monitor.Sintoma;
import monitor.Sintomas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;
import java.lang.reflect.Constructor;

public class MenuSintomas extends JFrame  implements ItemListener, ActionListener{

    private final JComboBox<String> combo1;
    private final JTextField registroSintoma;
    private final JButton registrar;
    private final Sintomas sintomas;

    JTable table;
    JScrollPane tableScollPanel;
    DefaultTableModel dataTable;

    public MenuSintomas(Sintomas sintomas)  {

        setBounds(120,120,500,450);
        this.sintomas = sintomas;
        setTitle("Registro Sintomas v1");
        setLayout(null);
        combo1 = new JComboBox<String>();
        JLabel categoria1 = new JLabel("Categoria");

        registrar = new JButton("Registrar");
        add(registrar);
        registrar.addActionListener(this);
        registrar.setBounds(300, 50, 90,  30);

        categoria1.setBounds(10,10,100,30);
        add(categoria1);
        JLabel sintomaText = new JLabel("sintoma");
        sintomaText.setBounds(10,50,100,30);
        add(sintomaText);
        registroSintoma = new JTextField();
        registroSintoma.setBounds(120,50,120,30);
        add(registroSintoma);
        combo1.setBounds(120,10,100,30);
        add(combo1);
        Archivo archivo = new Archivo();

        //tabla para mostrar sintomas
        dataTable = new DefaultTableModel();
        table = new JTable(dataTable);
        tableScollPanel = new JScrollPane(table);
        dataTable.addColumn("Cintoma");
        dataTable.addColumn("Categoria");
        table.setBackground(new Color(252,197,61));

        for(Sintoma sintoma: sintomas){
            String name = sintoma.toString();
            String categoria = sintoma.getClass().getName().split("\\.")[1];
            dataTable.addRow(new Object[]{name, categoria });
        }
        add(tableScollPanel);
        tableScollPanel.setBounds(10, 90, 450,300);

        List<String> listaClases;
        listaClases = archivo.clases();

        for(int i=0; i< listaClases.size(); i++){
            combo1.addItem(listaClases.get(i));
        }
        combo1.addItemListener(this);
        final MenuSintomas menuSintomas = this;
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent we){
                try {
                    archivo.guardar(sintomas);
                    synchronized(menuSintomas){
                        menuSintomas.notify();
                    }
                    menuSintomas.setVisible(false);
                    menuSintomas.dispose();

//                    for( Sintoma s: sintomas){
//                        System.out.println(s);
//                    }
//                    System.out.println("*******************");

                } catch (IOException e){
                    System.out.println("Error al guardar");
                }
            }
        });
        setVisible(true);
        synchronized(this){
            try{
                this.wait();
            }
            catch(InterruptedException ex){
                ex.printStackTrace();
            }
        }
    }

    public void itemStateChanged(ItemEvent e) {
        if (e.getSource()==combo1 && e.getStateChange() == ItemEvent.SELECTED) {
            String seleccionado = (String)combo1.getSelectedItem();
        }
    }

    public void actionPerformed(ActionEvent e) {
        Object botonPulsado = e.getSource();
        if (botonPulsado == registrar) {
            try {
                String name = "sintomas."+combo1.getSelectedItem();
                Class cl = Class.forName(name);
                Constructor constructor = cl.getConstructor(new Class[] {String.class} );
                Sintoma sintoma = (Sintoma)(constructor.newInstance(new Object[]{registroSintoma.getText().toLowerCase()}));
                if(validacion(sintoma)){
                    throw new Exception("Nombre de sintoma ya existe");
                }
                dataTable.addRow(new String[]{sintoma.toString(), (String) combo1.getSelectedItem()});
                sintomas.add(sintoma);
                for( Sintoma s: sintomas){
                    System.out.println(s);
                }
                System.out.println("*******************");

            } catch (Exception ex) {
//                ex.printStackTrace();
            }
        }
    }

    private boolean validacion(Sintoma sintoma){
        boolean sintomaExiste = false;
        String sintomaNuevo = sintoma.toString();
        for(Sintoma s: sintomas){
            if(s.toString().equals(sintomaNuevo)){
                sintomaExiste = true;
            }
        }
        return sintomaExiste;
    }
}
