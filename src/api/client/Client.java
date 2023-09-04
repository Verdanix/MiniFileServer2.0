package api.client;

import api.common.network.packets.data.RequestPacket;
import api.common.network.packets.data.ResponsePacketContainer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.function.BiConsumer;
import java.util.function.Consumer;


public class Client {
    private final String host;
    private final int port;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * Connects to the server and returns whether it did or didn't with an exception
     *
     * @param status True == connected, False + Exception == Couldn't connect
     * @return Client instance
     */
    public Client connect(BiConsumer<Boolean, Exception> status) {
        try {
            this.socket = new Socket(this.host, this.port);
            this.in = new DataInputStream(this.socket.getInputStream());
            this.out = new DataOutputStream(this.socket.getOutputStream());
        } catch (IOException e) {
            status.accept(false, e);
        }
        return this;
    }

    /**
     * Writes a packet to the server
     *
     * @param packet   The RequestPacket object you want to send
     * @param response A packet container for the response so you can get the status and message
     */
    public void write(RequestPacket packet, Consumer<ResponsePacketContainer> response) {
        try {
            packet.write(this.in, this.out);
            this.out.writeUTF("--EOP--");
            String packetId = this.in.readUTF();

            // End of packet
            response.accept(new ResponsePacketContainer(packetId, this.in.readInt(), this.in.readUTF()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Disconnects the client from the server
     *
     * @param status True == connected, False + Exception == Couldn't connect
     */
    public void disconnect(BiConsumer<Boolean, Exception> status) {
        try {
            this.socket.close();
            status.accept(true, null);
        } catch (Exception e) {
            status.accept(false, e);
        }
    }


    /**
     * Getter for host
     *
     * @return Host
     */
    public String getHost() {
        return host;
    }

    /**
     * Getter for the port
     *
     * @return Port
     */
    public int getPort() {
        return port;
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
     * Getter for the Socket InputStream
     *
     * @return DataInputStream
     */
    public DataInputStream getIn() {
        return in;
    }

    /**
     * Getter for the Socket OutputStream
     *
     * @return DataOutputStream
     */
    public DataOutputStream getOut() {
        return out;
    }

}
