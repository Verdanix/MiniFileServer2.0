package api.common.network.packets.response;

import api.common.network.packets.data.ResponsePacket;
import api.server.ClientSession;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Ping/Pong response packet
 */
public class PingResponsePacket extends ResponsePacket {
    public PingResponsePacket() {
        super("ping");
    }

    @Override
    public void write(ClientSession session, DataInputStream in, DataOutputStream out) {
        try {
            out.writeUTF("ping");
            out.writeInt(200);
            out.writeUTF("Pong!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
