package api.common.network.packets.response;

import api.common.network.packets.data.ResponsePacket;
import api.server.ClientSession;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class MessageResponsePacket extends ResponsePacket {

    public MessageResponsePacket() {
        super("msg");
    }

    @Override
    public void write(ClientSession session, DataInputStream in, DataOutputStream out) {
        // Read ALL data sent from the MessageRequestPacket
        try {
            String sender = in.readUTF();
            String message = in.readUTF();
            // Send status code
            out.writeInt(200);
            out.writeUTF(String.format("%s: %s", sender, message));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
