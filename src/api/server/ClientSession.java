package api.server;

import api.common.Configuration;
import api.common.network.packets.data.ResponsePacket;
import api.common.network.packets.response.InvalidResponsePacket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * Implementation of a Session for a client. It is run on a new thread which has its own read/write operations
 */
public class ClientSession {
    private final UUID clientId;
    private final DataInputStream in;
    private final DataOutputStream out;
    private final Socket socket;
    private boolean isLoggedIn = false;
    private String recentPacketId;

    public ClientSession(Socket socket) {
        this.socket = socket;
        try {
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            this.clientId = UUID.randomUUID();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Listens for incoming packets and responds accordingly
     *
     * @param exception Exception handling
     */
    public void listen(Consumer<Exception> exception) {
        try {
            while (!this.socket.isClosed()) {
                String packetId = this.in.readUTF();
                if (packetId.equals("--EOP--")) {
                    continue;
                }
                this.recentPacketId = packetId;
                ResponsePacket packet = Configuration.getResponses().get(packetId);
                if (packet == null) {
                    new InvalidResponsePacket().write(this, this.in, this.out);
                    continue;
                }
                packet.write(this, this.in, this.out);
            }
        } catch (EOFException ignored) {
            System.out.println("Client disconnected!");
        } catch (IOException e) {
            exception.accept(e);
        }
    }


    /**
     * Getter for the most recent PacketID
     *
     * @return PacketID
     */
    public String getRecentPacket() {
        return this.recentPacketId;
    }

    /**
     * Get's the Client's ID
     *
     * @return ClientID
     */
    public UUID getClientId() {
        return clientId;
    }

    /**
     * Getter for the Socket InputStream
     *
     * @return InputStream for the socket
     */
    public DataInputStream getIn() {
        return in;
    }

    /**
     * Getter for the Socket OutputStream
     *
     * @return OutputStream for the socket
     */
    public DataOutputStream getOut() {
        return out;
    }

    /**
     * Getter for the socket
     *
     * @return Socket
     */
    public Socket getSocket() {
        return socket;
    }

    /**
     * Getter for whether the user is logged in
     *
     * @return True or False
     */
    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    /**
     * Setter for whether the user is logged in or not
     *
     * @param loggedIn
     */
    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }
}
