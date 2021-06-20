package cargarregistros;

import monitor.Registros;

import java.io.*;

public class ArchivoRegistros {

    public Registros leerRegistros() throws IOException, ClassNotFoundException {
        ObjectInputStream file = new ObjectInputStream(new FileInputStream(directorioPath()));
        Registros registros = (Registros) file.readObject();
        file.close();
        return registros;
    }

    public boolean existeRegistros(){
        File f = new File(directorioPath());
        return f.exists();
    }

    public void guardarRegistros(Registros registros) throws IOException {
        ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream(directorioPath()));
        file.writeObject(registros);
        file.close();
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
            path = dir+separado+"cargarregistros"+separado+"registros.dat";
        } else {
            path = dir+separado+"src"+separado+"cargarregistros"+separado+"registros.dat";
        }
        return path;
    }
}
