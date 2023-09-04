package api.common.network.packets.request;

import api.common.network.packets.data.RequestPacket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Packet for logins
 */
public class LoginRequestPacket extends RequestPacket {
    private String username;
    private String password;

    public LoginRequestPacket(String username, String password) {
        super("login");
        this.username = username;
        this.password = password;
    }

    @Override
    public void write(DataInputStream in, DataOutputStream out) {
        try {
            out.writeUTF(this.getPacketId());
            out.writeUTF(this.username);
            out.writeUTF(this.password);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
