package cargarregistros;

import monitor.Registro;
import monitor.Registros;
import monitor.Sintomas;

import java.util.Date;

public class CargarRegistros {

    private Sintomas sintomas;
    private Registros registros;

//    private Sintoma perdidaDeOlfato;
//    private Sintoma temperaturaAlta;
//    private Sintoma tosSeca;

    public CargarRegistros(Sintomas sintomas) {
        this.sintomas = sintomas;
    }

    private void cargarRegistros() {
        ArchivoRegistros archivoRegistros = new ArchivoRegistros();
        registros = new Registros();
        if(archivoRegistros.existeRegistros()){
            try {
                for(Registro r: archivoRegistros.leerRegistros()){
                    registros.push(r);
                }
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    public Registro getRegistro() {
        cargarRegistros();
        Sintomas sintomasUsuario = new Sintomas();
        // Invocar UI
        // Ventana(sintomasMonitor, registros, sintomasUsuario)
        new MenuRegistros(sintomas, registros, sintomasUsuario);
        return new Registro(new Date(),sintomasUsuario);
    }

}
