package api.server;

import api.common.Configuration;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * Creates a Main server
 */
public class Server {
    private final List<ClientSession> sessions = new ArrayList<>();
    private final String host;
    private final int port;
    private ServerSocket socket;

    public Server(Configuration config) {
        this.host = config.getHost();
        this.port = config.getPort();
    }

    /**
     * Starts the server
     *
     * @param status True = successful attempt, false + exception == unsuccessful attempt
     * @return Server instance
     */
    public Server startServer(BiConsumer<Boolean, Exception> status) {
        try (ServerSocket serverSocket = new ServerSocket(this.port, 0, InetAddress.getByName(this.host))) {
            this.socket = serverSocket;
            status.accept(true, null);
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                Thread thread = new Thread(() -> {
                    ClientSession session = new ClientSession(socket);
                    this.sessions.add(session);
                    session.listen(e -> {
                        status.accept(false, e);
                    });
                    this.sessions.remove(session);
                });
                thread.start();
            }
        } catch (IOException e) {
            status.accept(false, e);
        }
        return this;
    }

    /**
     * Stops the server
     *
     * @param status True = successful attempt, false + exception == unsuccessful attempt
     */
    public void stop(BiConsumer<Boolean, Exception> status) {
        try {
            this.socket.close();
            status.accept(true, null);
        } catch (IOException e) {
            status.accept(false, e);
        }
    }

    /**
     * Getter for sessions on the server
     *
     * @return ClientSession
     */
    public List<ClientSession> getSessions() {
        return sessions;
    }

    /**
     * Getter for the server host
     *
     * @return Server hostname
     */
    public String getHost() {
        return host;
    }

    /**
     * Getter for the server's port
     *
     * @return Server port
     */
    public int getPort() {
        return port;
    }
}
