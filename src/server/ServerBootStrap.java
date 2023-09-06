package server;

import api.common.Configuration;
import api.server.Server;

public class ServerBootStrap {

    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Please pass in the username, password, host, and port as CLI arguments!");
            return;
        }
        final String username;
        final String password;
        final String host;
        final int port;
        try {
            username = args[0];
            password = args[1];
            host = args[2];
            port = Integer.parseInt(args[3]);
        } catch (NumberFormatException ignored) {
            System.out.println("Please pass in the username, password, host, and port as CLI arguments!");
            return;
        }
        Server server = new Server(Configuration.getInstance()
                .setHost(host)
                .setPort(port)
                .setUsername(username)
                .setPassword(password))
                .startServer((b, e) -> {
                    if (b) {
                        System.out.printf("Successfully started server on %s:%d\n", host, port);
                    } else {
                        System.out.printf("Couldn't start server. \nReason: %s\n", e.getMessage());
                    }
                });
    }
}
