package api.common.network.packets.data;

public class ResponsePacketContainer {
    private final String packetId;
    private final int status;
    private final String message;

    public ResponsePacketContainer(String packetId, int status, String message) {
        this.packetId = packetId;
        this.status = status;
        this.message = message;
    }

    /**
     * Getter for PacketID
     *
     * @return PacketID
     */
    public String getPacketId() {
        return packetId;
    }

    /**
     * Getter for the status
     *
     * @return Status
     */
    public int getStatus() {
        return status;
    }

    /**
     * Getter for the message
     *
     * @return Message
     */
    public String getMessage() {
        return message;
    }
}
