package logica;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FileMonitor implements Runnable {

    private String localFolder;

    public FileMonitor(String localFolder) {
        this.localFolder = localFolder;
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
                    }
                }
            }
            for (String fileName : oldFilesMap.keySet()) {
                if (!filesMap.containsKey(fileName)) {
                    //implementacion de la sincronizacion
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
