package api.common.network.packets.request;

import api.common.network.packets.data.RequestPacket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class InfoRequestPacket extends RequestPacket {
    public InfoRequestPacket() {
        super("info");
    }

    @Override
    public void write(DataInputStream in, DataOutputStream out) throws IOException {
        out.writeUTF(this.getPacketId());
    }
}
