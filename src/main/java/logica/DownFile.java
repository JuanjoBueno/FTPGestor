package logica;

import logica.security.AESSimpleManager;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.util.Scanner;

public class DownFile {
    private static final String DOWN_FOLDER = "/Users/juanjobueno/Desktop/DescargasNubesilla/";
    private static final String FTP_FOLDER = "/";
    private static final String AES_PASSWORD = "MiguelAngelgraciasporelcursodeLosMasInteresantes";

    private static void descargarArchivo(String archivoRemoto, String destinoLocal) {
        try {
            FTPManager ftpManager = new FTPManager();
            ftpManager.connect();
            System.out.println("Descargando: " + archivoRemoto);
            try (OutputStream outputStream = new FileOutputStream(destinoLocal)) {
                boolean success = ftpManager.getClienteFTP().retrieveFile(FTP_FOLDER + archivoRemoto, outputStream);
                if (success) {
                    System.out.println("Archivo descargado: " + destinoLocal);
                    descifrarArchivo(destinoLocal);
                } else {
                    System.out.println("No se encontró el archivo en el servidor FTP.");
                }
            }
        } catch (Exception e) {
            System.err.println("Error al descargar el archivo: " + archivoRemoto);
            e.printStackTrace();
        }
    }

    private static void descifrarArchivo(String archivoCifrado) {

        try {
            String textoCifrado = new String(Files.readAllBytes(Paths.get(archivoCifrado)));
            Key clave = AESSimpleManager.obtenerClave(AES_PASSWORD, 32);
            String textoDescifrado = AESSimpleManager.descifrar(textoCifrado, clave);

            try (FileOutputStream fos = new FileOutputStream(archivoCifrado)) {
                fos.write(textoDescifrado.getBytes());
            }

            System.out.println("Archivo descifrado correctamente: " + archivoCifrado);

        } catch (Exception e) {
            System.out.println("Error al descifrar el archivo: " + archivoCifrado);
            e.printStackTrace();
        }
    }

    private static void listarArchivosRemotos() {
        try {
            FTPManager ftpManager = new FTPManager();
            ftpManager.connect();
            String[] archivosRemotos = ftpManager.getClienteFTP().listNames(FTP_FOLDER);
            if (archivosRemotos != null) {
                for (String archivoRemoto : archivosRemotos) {
                    System.out.println( "Archivo remoto: " + archivoRemoto);
                }
            }
        } catch (Exception e) {
            System.err.println("Error al listar los archivos remotos: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        System.out.print("Ingrese el nombre del archivo a descargar:\n ");

        try {
            FTPManager ftpManager = new FTPManager();
            ftpManager.connect();
            listarArchivosRemotos();

            String archivoRemoto = teclado.nextLine();
            descargarArchivo(archivoRemoto, DOWN_FOLDER + archivoRemoto);
            ftpManager.disconnect();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
