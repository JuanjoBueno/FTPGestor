package logica;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;

public class FTPManager {

    private FTPClient clienteFTP;
    private final String server;
    private final int port;
    private final String user;
    private final String password;

    public FTPManager(String server, int port, String user, String password) {
        this.server = server;
        this.port = port;
        this.user = user;
        this.password = password;
        this.clienteFTP = new FTPClient();
    }

    private void connect() throws IOException {
        clienteFTP.connect(server, port);
        int respuesta = clienteFTP.getReplyCode();

        if (!FTPReply.isPositiveCompletion(respuesta)) {
            clienteFTP.disconnect();
            throw new IOException("Error al conectar con el servidor FTP");
        }

        if (!clienteFTP.login(user, password)) {
            clienteFTP.logout();
            throw new IOException("Error: credenciales incorrectas.");
        }

        clienteFTP.enterLocalPassiveMode(); // Mejora compatibilidad con algunos servidores
        clienteFTP.setFileType(FTP.BINARY_FILE_TYPE);
    }

    private void disconnect() {
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
