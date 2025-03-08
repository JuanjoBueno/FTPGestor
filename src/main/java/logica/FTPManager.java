package logica;

import org.apache.commons.net.ftp.FTPClient;

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




}
