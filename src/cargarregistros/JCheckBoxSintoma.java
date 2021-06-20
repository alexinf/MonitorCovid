package cargarregistros;

import monitor.Sintoma;

import javax.swing.*;

public class JCheckBoxSintoma extends JCheckBox {
    private Sintoma sintoma;

    public JCheckBoxSintoma(Sintoma sintoma){
        super();
        this.sintoma = sintoma;
    }

    public Sintoma getSintoma() {
        return sintoma;
    }
}
