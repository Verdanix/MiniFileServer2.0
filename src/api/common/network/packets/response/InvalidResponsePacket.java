package api.common.network.packets.response;

import api.common.network.packets.data.ResponsePacket;
import api.server.ClientSession;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Send when there is response for the specified PacketID
 */
public class InvalidResponsePacket extends ResponsePacket {

    public InvalidResponsePacket() {
        super("invalidPacket");
    }

    @Override
    public void write(ClientSession session, DataInputStream in, DataOutputStream out) {
        try {
            /*
            This essentially reads all bytes until it detects an EOP (End of packet) then it write the status, and message
             */
            StringBuilder builder = new StringBuilder();
            int read;
            int length;
            while ((read = in.read()) != -1) {
                builder.append((char) read);
                length = builder.length();
                if (builder.length() < 7) {
                    continue;
                }
                if (builder.subSequence(length - 7, length).equals("--EOP--")) {
                    break;
                }
            }
            out.writeUTF(this.getPacketId());
            out.writeInt(500);
            out.writeUTF(String.format("Invalid packet with ID \"%s\"", session.getRecentPacket()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
