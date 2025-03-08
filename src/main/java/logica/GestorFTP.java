package logica;

import java.io.File;

public class GestorFTP {

    public static void main(String[] args) {
        String localFolder = "/Users/juanjobueno/Desktop/DesktopNube/";
        String remoteFolder = "/";

        //validar que la carpeta de sincronizacion exista
        File folder = new File(localFolder);
        if (!folder.exists()) {
            folder.mkdirs();
            System.out.println("Carpeta 'nubesita' creada.");
        }

        FileMonitor fileMonitor = new FileMonitor(localFolder, remoteFolder);
        new Thread(fileMonitor).start();

    }
}
