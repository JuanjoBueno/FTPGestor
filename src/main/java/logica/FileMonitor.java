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

    public void monitor() {

        Map<String, Long> oldFilesMap = new HashMap<>();
        System.out.println("Monitor de archivos iniciado en la carpeta " + localFolder);

        while (true) {
            File folder = new File(localFolder);
            File[] files = folder.listFiles();
            Map<String, Long> filesMap = new HashMap<>();

            if (files != null) {
                for (File file : files) {
                    filesMap.put(file.getName(), file.lastModified());

                    if (!oldFilesMap.containsKey(file.getName()) || oldFilesMap.get(file.getName()) != file.lastModified()) {
                        //implementacion de la sincronizacion
                        new Thread(new UpFile(file.getPath(), remoteFolder)).start();
                    }
                }
            }
            for (String fileName : oldFilesMap.keySet()) {
                if (!filesMap.containsKey(fileName)) {
                    //implementacion de la sincronizacion
                    new Thread(new DeleteFile(fileName, remoteFolder)).start();
                }
            }
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
