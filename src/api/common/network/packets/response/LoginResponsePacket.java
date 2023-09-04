package api.common.network.packets.response;

import api.common.network.packets.data.ResponsePacket;
import api.server.ClientSession;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Response Packet for logins
 */
public class LoginResponsePacket extends ResponsePacket {
    public LoginResponsePacket() {
        super("login");
    }

    @Override
    public void write(ClientSession session, DataInputStream in, DataOutputStream out) {
        try {
            String username = in.readUTF();
            String password = in.readUTF();
            out.writeUTF(this.getPacketId());
            if (session.isLoggedIn()) {
                out.writeInt(200);
                out.writeUTF("Already Logged in");
            } else if (this.getConfig().getUsername().equals(username) && this.getConfig().getPassword().equals(password)) {
                out.writeInt(200);
                out.writeUTF("Successfully logged in!");
                session.setLoggedIn(true);
            } else {
                out.writeInt(401);
                out.writeUTF("Invalid username or password!");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
