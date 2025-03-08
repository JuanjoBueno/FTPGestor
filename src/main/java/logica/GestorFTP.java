package logica;

import java.io.File;

public class GestorFTP {

    public static void main(String[] args) {
        String localFolder = "./nubesita"; //Carpeta de sincronizacion
        String server = "localhost";
        int port = 21;
        String user = "user";
        String password = "password";

        //validar que la carpeta de sincronizacion exista
        File folder = new File(localFolder);
        if (!folder.exists()) {
            folder.mkdirs();
            System.out.println("Carpeta 'nubesita' creada.");
        }

    }
}
