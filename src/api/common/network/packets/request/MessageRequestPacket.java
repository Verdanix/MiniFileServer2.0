package api.common.network.packets.request;

import api.common.network.packets.data.RequestPacket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class MessageRequestPacket extends RequestPacket {
    private String sender;
    private String message;

    public MessageRequestPacket(String sender, String message) {
        super("msg");
    }

    @Override
    public void write(DataInputStream in, DataOutputStream out) {
        try {
            out.writeUTF(this.getPacketId());
            out.writeUTF(this.sender);
            out.writeUTF(this.message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
