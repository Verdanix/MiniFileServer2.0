package example;

import api.client.Client;
import api.common.network.packets.request.LoginRequestPacket;
import api.common.network.packets.request.PingRequestPacket;
import api.common.network.packets.request.TestInvalidRequestPacket;

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
        client.write(new TestInvalidRequestPacket(), response1 -> {
            //Invalid Request
            System.out.println(response1.getMessage());

            client.write(new LoginRequestPacket("username", "password"), response2 -> {
                // Invalid username or password!
                System.out.println(response2.getMessage());

                client.write(new LoginRequestPacket("username", "password1234"), response3 -> {
                    // Successfully logged in!
                    System.out.println(response3.getMessage());

                    client.write(new PingRequestPacket(/*"username", "password124"*/), response4 -> {
                        // Already Logged in
                        System.out.println(response4.getMessage());
                    });
                });
            });
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
