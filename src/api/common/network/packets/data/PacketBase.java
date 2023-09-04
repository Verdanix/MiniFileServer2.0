package api.common.network.packets.data;

/**
 * Base class for ALL packets
 */
public abstract class PacketBase {
    private final String packetId;

    public PacketBase(String packetId) {
        this.packetId = packetId;
    }

    /**
     * Getter for the packetId
     *
     * @return PacketID
     */
    public String getPacketId() {
        return packetId;
    }
}
