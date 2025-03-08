package logica.syncThreads;

import logica.FTPManager;

import java.io.IOException;

public class DeleteFile implements Runnable {

    private String pathName;
    private String remoteFolder;

    public DeleteFile(String pathName, String remoteFolder) {
        this.pathName = pathName;
        this.remoteFolder = remoteFolder;
    }
    @Override
    public void run() {
        FTPManager ftpManager = new FTPManager();
        try {
            ftpManager.connect();
            ftpManager.getClienteFTP().deleteFile(remoteFolder + pathName);
            ftpManager.disconnect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
