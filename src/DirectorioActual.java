import java.io.File;
public class DirectorioActual {
    public static void main (String args[]) {
        File miDir = new File (".");
        try {
            System.out.println ("Directorio actual: " + miDir.getCanonicalPath());
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}