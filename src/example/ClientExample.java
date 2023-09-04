package example;

import api.client.Client;
import api.common.network.packets.request.MessageRequestPacket;

public class ClientExample {

    public static void main(String[] args) {
        Client client = new Client("localhost", 19133)
                .connect((b, e) -> {
                    if (b) {
                        System.out.println("Successfully connected to server.");
                    } else {
                        System.out.println(e.getMessage());
                    }
                });
        // Create an instance of MessageRequestPacket and pass in the arguments
        client.write(new MessageRequestPacket("TestSender", "I see you"), response -> {

            System.out.println(response.getMessage()); // TestSender: I see  you
        });
        // Disconnects successfully
        client.disconnect((b, e) -> {
            if (b) {
                System.out.println("Successfully disconnected!");
            } else {
                System.out.printf("Couldn't successfully disconnected: %s!\n", e.getMessage());
            }
        });
    }
}
