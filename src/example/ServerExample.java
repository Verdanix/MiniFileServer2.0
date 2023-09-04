package example;

import api.common.Configuration;
import api.server.Server;

public class ServerExample {

    public static void main(String[] args) {
        Server server = new Server(
                Configuration.getInstance()
                        .setHost("localhost")
                        .setPort(19133)
                        .setUsername("username")
                        .setPassword("password1234")
        );

        String host = Configuration.getInstance().getHost();
        int port = Configuration.getInstance().getPort();
        server.startServer((b, e) -> {
            if (b) {
                System.out.printf("Successfully started the server on %s:%d\n", host, port);
            } else {
                System.out.printf("Unable to successfully start the server on %s:%d. Reason: %s", host, port, e.getMessage());
            }
        });
    }
}
