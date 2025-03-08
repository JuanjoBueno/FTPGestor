package logica;

import java.io.File;

public class GestorFTP {

    public static void main(String[] args) {
        String localFolder = "./nubesita"; //Carpeta de sincronizacion

        //validar que la carpeta de sincronizacion exista
        File folder = new File(localFolder);
        if (!folder.exists()) {
            folder.mkdirs();
            System.out.println("Carpeta 'nubesita' creada.");
        }

    }
}
