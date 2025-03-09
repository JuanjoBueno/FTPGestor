package logica;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Clase para manejar la comunicación con el servidor FTP
public class FTPManager {

    private FTPClient clienteFTP;
    private final String SERVER = "localhost";
    private final int PORT = 21;
    private final String USER = "juanjobueno";
    private final String PASSWORD = "_Noa007_";

    public FTPManager() {
        clienteFTP = new FTPClient();
    }

    // Getters
    public FTPClient getClienteFTP() {
        return clienteFTP;
    }

    //Metodo de conexion
    public void connect() throws IOException {
        clienteFTP.connect(SERVER, PORT);
        int respuesta = clienteFTP.getReplyCode();

        // Verificar si la conexión fue exitosa
        if (!FTPReply.isPositiveCompletion(respuesta)) {
            clienteFTP.disconnect();
            throw new IOException("Error al conectar con el servidor FTP");
        }

        if (!clienteFTP.login(USER, PASSWORD)) {
            clienteFTP.logout();
            throw new IOException("Error: credenciales incorrectas.");
        }

        clienteFTP.enterLocalPassiveMode(); // Mejora compatibilidad con algunos servidores
        clienteFTP.setFileType(FTP.BINARY_FILE_TYPE);
    }

    //Metodo de desconexion
    public void disconnect() {
        try {
            if (clienteFTP.isConnected()) {
                clienteFTP.logout();
                clienteFTP.disconnect();
            }
        } catch (IOException e) {
            System.err.println("Error al desconectar: " + e.getMessage());
        }
    }
}
