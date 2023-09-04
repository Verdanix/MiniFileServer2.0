package api.common.network.packets.data;

import api.common.Configuration;
import api.server.ClientSession;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * Base class for the ResponsePackets
 */
public abstract class ResponsePacket {
    private final String packetId;
    private final Configuration config;

    public ResponsePacket(String packetId) {
        this.packetId = packetId;
        this.config = Configuration.getInstance();
    }

    public abstract void write(ClientSession session, DataInputStream in, DataOutputStream out);

    public String getPacketId() {
        return packetId;
    }

    public Configuration getConfig() {
        return config;
    }
}
