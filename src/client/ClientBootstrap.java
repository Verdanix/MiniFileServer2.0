package client;

import api.client.Client;
import api.common.network.packets.data.RequestPacket;
import api.common.network.packets.request.InfoRequestPacket;
import api.common.network.packets.request.LoginRequestPacket;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ClientBootstrap {
    private static ClientBootstrap INSTANCE;
    private final HashMap<String, String> commands = new HashMap<>();

    public static ClientBootstrap getInstance() {
        if (ClientBootstrap.INSTANCE == null) {
            ClientBootstrap.INSTANCE = new ClientBootstrap();
        }
        return INSTANCE;
    }

    public static void main(String[] args) {
        ClientBootstrap main = ClientBootstrap.getInstance();
        main.loadHelp();
        if (args.length != 2) {
            System.out.println("Please pass in the host and port as CLI arguments!");
            return;
        }

        final String host;
        final int port;
        try {

            host = args[0];
            port = Integer.parseInt(args[1]);
        } catch (NumberFormatException ignored) {
            System.out.println("Please pass in the username, password, host, and port as CLI arguments!");
            return;
        }
        Client client = new Client(host, port)
                .connect((b, e) -> {
                    if (b) {
                        System.out.printf("Successfully connected to remote server on %s:%d\n", host, port);
                    } else {
                        System.out.printf("Couldn't connect to server. \nReason: %s\n", e.getMessage());
                        System.exit(0);
                    }
                });

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("$ ");
                String command = scanner.nextLine();
                System.out.println();
                if (command.equalsIgnoreCase("help")) {
                    main.printHelp();
                    continue;
                }
                RequestPacket cmd = getPacketFromCmd(command);
                if (cmd == null) {
                    System.out.println("Invalid command. Enter help to get a list of commands.");
                    continue;
                }
                client.write(scanner, cmd, response -> {
                    System.out.printf("\nServer -> %d\n<-------Response------->\n", response.getStatus());
                    System.out.printf("%s\n", response.getMessage());
                    System.out.println("<-------Response------->\n");
                });
            }
        }
    }

    private static RequestPacket getPacketFromCmd(String command) {
        return switch (command.toLowerCase()) {
            case "info" -> new InfoRequestPacket();
            case "login" -> new LoginRequestPacket();
            default -> null;
        };
    }

    private void loadHelp() {
        this.commands.put("info", "Get information about the system.");
        this.commands.put("login", "Login to interact.");
    }

    private void printHelp() {
        System.out.println("-----Help-----");
        for (Map.Entry<String, String> s : this.commands.entrySet()) {
            System.out.printf("%s -> %s\n", s.getKey(), s.getValue());
        }
        System.out.println("--------------");
    }
}
