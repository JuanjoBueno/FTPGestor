package logica;

import logica.syncThreads.DeleteFile;
import logica.syncThreads.UpFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FileMonitor implements Runnable {

    private String localFolder;
    private String remoteFolder;

    public FileMonitor(String localFolder, String remoteFolder) {
        this.localFolder = localFolder;
        this.remoteFolder = remoteFolder;
    }

    //Funcion que monitorea los archivos en la carpeta local
    public void monitor() {

        Map<String, Long> oldFilesMap = new HashMap<>();
        System.out.println("Monitor de archivos iniciado en la carpeta " + localFolder);

        while (true) {
            //obtener los archivos de la carpeta
            File folder = new File(localFolder);
            File[] files = folder.listFiles();
            Map<String, Long> filesMap = new HashMap<>();

            //Mapeo de los archivos guardando nombre y ultima modificacion
            if (files != null) {
                for (File file : files) {
                    filesMap.put(file.getName(), file.lastModified());

                    //Validar si el archivo ya existia y si su ultima modificacion es diferente
                    if (!oldFilesMap.containsKey(file.getName()) || oldFilesMap.get(file.getName()) != file.lastModified()) {
                        //Lanzar un hilo para subir el archivo
                        new Thread(new UpFile(file.getPath(), remoteFolder)).start();
                    }
                }
            }
            //Mapear los archivos que no estan en la carpeta local para borrarlos
            for (String fileName : oldFilesMap.keySet()) {
                if (!filesMap.containsKey(fileName)) {
                    //Lanzar un hilo para borrar el archivo
                    new Thread(new DeleteFile(fileName, remoteFolder)).start();
                }
            }
            //Actualizar la lista de archivos
            oldFilesMap = filesMap;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        monitor();
    }
}
