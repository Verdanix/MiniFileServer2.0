package api.common.network.packets.request;

import api.common.network.packets.data.RequestPacket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Tests the invalid packet system,
 */
public class TestInvalidRequestPacket extends RequestPacket {
    public TestInvalidRequestPacket() {
        super("invalidRequest");
    }

    @Override
    public void write(DataInputStream in, DataOutputStream out) {
        try {
            out.writeUTF("invalidRequest");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
