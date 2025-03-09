package logica;

import java.io.File;

public class GestorFTP {

    //Clase principal
    public static void main(String[] args) {
        String localFolder = "/Users/juanjobueno/Desktop/DesktopNube/";
        String remoteFolder = "/";

        //validar que la carpeta de sincronizacion exista
        File folder = new File(localFolder);
        if (!folder.exists()) {
            folder.mkdirs();
            System.out.println("Carpeta 'nubesita' creada.");
        }

        //Instanciamos el monitor y lanzamos el hilo
        FileMonitor fileMonitor = new FileMonitor(localFolder, remoteFolder);
        new Thread(fileMonitor).start();

    }
}
