package cargarsintomas;

import monitor.Sintoma;
import monitor.Sintomas;

public class CargarSintomas {

    private Sintomas sintomas;
    private Sintoma perdidaDeOlfato;
    private Sintoma temperaturaAlta;
    private Sintoma tosSeca;

    public CargarSintomas() {
        cargarSintoma();
        cargarSintomas();
    }

    private void cargarSintoma() {
        Archivo archivo = new Archivo();
        sintomas = new Sintomas();
        if(archivo.existeArchivo()){
            try {
                for(Sintoma s: archivo.leerSintomas()){
                    sintomas.add(s);
                }
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    private void cargarSintomas() {
        new MenuSintomas(sintomas);
    }

    public Sintomas getSintomas() {
        return sintomas;
    }

}
