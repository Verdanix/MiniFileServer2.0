# My mini protocol.
## You can use it however you want by adding your own packets.
---

# Creating server
To make a very minimum server, do this
```java

// Call the stop method to stop the server
// This server runs on localhost:19133 with username "username" and password "password1234"
Server server = new Server(
        Configuration.getInstance()
                .setHost("localhost")
                .setPort(19133)
                .setUsername("username")
                .setPassword("password1234")
).startServer((b, e) -> {
    if (b) {
        System.out.println("Started Server");
    } else {
        System.out.printf("Unable to successfully start the server. Reason: %s", host, port, e.getMessage());
    }
});
```

## Creating a client
```java
// Connecting to server on localhost:19133
Client client = new Client("localhost", 19133)
        .connect((b, e) -> {
            if (b) {
                System.out.println("Successfully connected to server.");
            } else {
                System.out.println(e.getMessage());
            }
        });
```

# Adding packets
## Creating request packet
Create a class that inherits the RequestPacket. For this, we are going to make a messaging packet

```java
public class MessageRequestPacket extends RequestPacket {
    private String sender;
    private String message;
    public MessageRequestPacket(String sender, String message) {
        super("msg");
        this.sender = sender;
        this.message = message;
    }

    @Override
    public void write(DataInputStream in, DataOutputStream out) {
        try {
            
            out.writeUTF(this.getPacketId()); // Always make sure to send the packet ID before anything like this
            // Send any other data
            out.writeUTF(this.sender); 
            out.writeUTF(this.message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
```
## Creating a response packet. 
By default, the server will send an InvalidPacket response, which signals no request handler. Create a new class that inherits ResponsePacket

```java
public class MessageResponsePacket extends ResponsePacket {

    public MessageResponsePacket() {
        super("msg");
    }

    @Override
    public void write(ClientSession session, DataInputStream in, DataOutputStream out) {
        // Read ALL data sent from the MessageRequestPacket
        try {
            String sender = in.readUTF();
            String message = in.readUTF();

            // Always send packet ID
            out.writeUTF(this.getPacketId());
            // Send status code
            out.writeInt(200);
            out.writeUTF(String.format("%s: %s", sender, message));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

```
# Sending Packets
Using the client object you created earlier, you can use the write method which takes a RequestPacket and reuturns a packet container that holds the ID, status, and message.
```java
// Create an instance of MessageRequestPacket and pass in the arguments
client.write(new MessageRequestPacket("TestSender", "I see you"), response -> {
    System.out.println(response.getMessage()); // TestSender: I see  you
});
```

---

## Other methods
### Client#disconnect
```java
// Disconnects successfully
client.disconnect((b, e) -> {
    if (b) {
        System.out.println("Successfully disconnected!");
    } else {
        System.out.printf("Couldn't successfully disconnected: %s!\n", e.getMessage());
    }
});
```

Hopefully there are some new cool projects you can make with this. 
