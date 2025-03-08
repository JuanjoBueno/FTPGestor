package logica.syncThreads;

import logica.FTPManager;
import logica.security.AESSimpleManager;
import java.io.*;

public class UpFile implements Runnable {

    private String path;
    private String remoteFolder;

    public UpFile(String path, String remoteFolder) {
        this.remoteFolder = remoteFolder;
        this.path = path;
    }

    @Override
    public void run() {
        FTPManager ftpManager = new FTPManager();
        File file = new File(path);
        try {
            if (!file.exists()) {
                return;
            }
            System.out.println("Subiendo archivo: " + file.getName());
            ftpManager.connect();

            FileInputStream fis = new FileInputStream(file);
            byte[] contenido = fis.readAllBytes();
            fis.close();

            String contenidoAES = AESSimpleManager.cifrar(new String(contenido), AESSimpleManager.obtenerClave("password", 32));
            if (ftpManager.getClienteFTP().listNames(file.getName()) != null) {
                ftpManager.getClienteFTP().deleteFile(file.getName());
            }
            InputStream isAES = new ByteArrayInputStream(contenidoAES.getBytes());
            boolean success = ftpManager.getClienteFTP().storeFile( remoteFolder + file.getName(), isAES);
            isAES.close();

            if (success) {
                System.out.println("Archivo subido correctamente: " + file.getName());
            } else {
                System.out.println("Error al subir el archivo: " + file.getName());
            }
            ftpManager.disconnect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            ftpManager.disconnect();
        }

    }
}
