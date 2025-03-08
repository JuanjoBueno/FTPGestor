package logica.syncThreads;

import logica.FTPManager;

import java.io.IOException;

public class DeleteFile implements Runnable {

    private String pathName;

    public DeleteFile(String pathName) {
        this.pathName = pathName;
    }
    @Override
    public void run() {
        FTPManager ftpManager = new FTPManager();
        try {
            ftpManager.connect();
            ftpManager.getClienteFTP().deleteFile(pathName);
            ftpManager.disconnect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
