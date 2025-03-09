package logica.syncThreads;

import logica.FTPManager;
import logica.security.AESSimpleManager;
import java.io.*;

public class UpFile implements Runnable {

    private final String path;
    private final String remoteFolder;

    public UpFile(String path, String remoteFolder) {
        this.remoteFolder = remoteFolder;
        this.path = path;
    }

    @Override
    public void run() {

        // Verificar si el archivo existe
        File file = new File(path);
        if (!file.exists()) {
            System.err.println("El archivo no existe: " + path);
            return;
        }

        System.out.println("Subiendo archivo: " + file.getName());

        try {
            FTPManager ftpManager = new FTPManager();
            ftpManager.connect();

            // Leer el archivo
            byte[] contenido;
            try (FileInputStream fis = new FileInputStream(file)) {
                contenido = fis.readAllBytes();
            }

            // Cifrar contenido
            String contenidoAES = AESSimpleManager.cifrar(
                    new String(contenido),
                    AESSimpleManager.obtenerClave("MiguelAngelgraciasporelcursodeLosMasInteresantes", 32)
            );

            // Verificar si es una modificacion y borrar si es necesario
            if (ftpManager.getClienteFTP().listNames(remoteFolder + file.getName()) != null) {
                ftpManager.getClienteFTP().deleteFile(remoteFolder + file.getName());
            }

            // Subir archivo cifrado
            boolean success;
            try (InputStream isAES = new ByteArrayInputStream(contenidoAES.getBytes())) {
                success = ftpManager.getClienteFTP().storeFile(remoteFolder + file.getName(), isAES);
            }

            if (success) {
                System.out.println("✅ Archivo subido correctamente: " + file.getName());
            } else {
                System.err.println("❌ Error al subir el archivo: " + file.getName());
            }

        } catch (IOException e) {
            System.err.println("Error de I/O al procesar el archivo: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error general: " + e.getMessage());
        }
    }
}

