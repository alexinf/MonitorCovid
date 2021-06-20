package cargarsintomas;

import monitor.Sintoma;
import monitor.Sintomas;

import java.io.*;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class Archivo {

    public List<String> clases()  {
        List<String> listaClasesPaquete = new ArrayList<>();
        try {
            File[] classes = this.archivosPaquete();
            Class<Sintoma> sintomaClass = Sintoma.class;
            for (File clase: classes){
                try {
                    String nombreClase = clase.getName().split("\\.")[0];
                    Class.forName("sintomas"+"."+nombreClase).asSubclass(sintomaClass);
                    listaClasesPaquete.add(nombreClase);
                } catch ( Exception e) {
                    System.out.println("Clase "+ clase.getName()+" descartada.");
                }
            }
        } catch (IOException e){
            System.out.println("Paquete inexistente");
        }
        return listaClasesPaquete;
    }

    public boolean existeArchivo(){
        File f = new File(directorioPath());
        return f.exists();
    }

    public void guardar(Sintomas sintomas) throws IOException {

        ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream(directorioPath()));
        file.writeObject(sintomas);
        file.close();
    }

    public Sintomas leerSintomas() throws IOException, ClassNotFoundException {

        ObjectInputStream file = new ObjectInputStream(new FileInputStream(directorioPath()));
        Sintomas sintomas = (Sintomas) file.readObject();
        file.close();
        return sintomas;
    }

    private File[] archivosPaquete() throws IOException {
        File dir = null;
        Enumeration<URL> urls = Thread.currentThread().getContextClassLoader()
                .getResources("sintomas");
        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            dir = new File(url.getFile());
        }
        return  dir.listFiles();
    }

    private String directorioPath(){
        File miDir = new File (".");
        String dir="";
        try {
            dir= miDir.getCanonicalPath();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        String path = "";
        String separado = System.getProperty("file.separator");

        boolean esDesarrollo = false;
        File file2 = new File(dir);
        String[] a = file2.list();
        for(int i=0; i<a.length; i++){
            if(a[i].equals("src")){
                esDesarrollo=true;
            }
        }

        if ( !esDesarrollo ){
            path = dir+separado+"cargarsintomas"+separado+"sintomas.dat";
        } else {
            path = dir+separado+"src"+separado+"cargarsintomas"+separado+"sintomas.dat";
        }
        return path;
    }
}
