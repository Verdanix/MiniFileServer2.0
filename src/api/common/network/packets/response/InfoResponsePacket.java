package api.common.network.packets.response;

import api.common.network.packets.data.ResponsePacket;
import api.server.ClientSession;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InfoResponsePacket extends ResponsePacket {
    public InfoResponsePacket() {
        super("info");
    }

    @Override
    public void write(ClientSession session, DataInputStream in, DataOutputStream out) throws IOException {
        out.writeUTF(this.getPacketId());
        if (!session.isLoggedIn()) {
            out.writeInt(401);
            out.writeUTF("Unauthorized");
        } else {
            out.writeInt(200);
            Date uptime = new Date(ManagementFactory.getRuntimeMXBean().getUptime());
            String formattedTime = new SimpleDateFormat("HH:mm:ss").format(uptime);
            String message = String.format("""
                            OS: %s
                            Cores: %d
                            Memory Usage: %.2f
                            Uptime: %s""",
                    System.getProperty("os.name"),
                    Runtime.getRuntime().availableProcessors(),
                    this.getRamPercentage(),
                    formattedTime
            );
            out.writeUTF(message);
        }
    }

    private double getRamPercentage() {
        final long RAM_TOTAL = Runtime.getRuntime().totalMemory();
        final long RAM_FREE = Runtime.getRuntime().freeMemory();
        final long RAM_USED = RAM_TOTAL - RAM_FREE;

        return ((double) RAM_USED / RAM_TOTAL) * 100;

    }
}
