package api.common.network.packets.data;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * Base class for the RequestPackets
 */
public abstract class RequestPacket extends PacketBase {
    public RequestPacket(String packetId) {
        super(packetId);
    }

    /**
     * Writes to the server
     *
     * @param in  InputStream
     * @param out OutputStream
     */
    public abstract void write(DataInputStream in, DataOutputStream out);
}
