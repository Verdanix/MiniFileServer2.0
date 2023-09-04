package api.common.network.packets.request;

import api.common.network.packets.data.RequestPacket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Ping Pong Packet
 */
public class PingRequestPacket extends RequestPacket {

    public PingRequestPacket() {
        super("ping");
    }

    @Override
    public void write(DataInputStream in, DataOutputStream out) {
        try {
            out.writeUTF(this.getPacketId());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
