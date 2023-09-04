package api.common;

import api.common.network.packets.data.ResponsePacket;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Singleton class for server/client Configuration
 */
public class Configuration {
    private static final HashMap<String, ResponsePacket> responses = new HashMap<>();
    private static Configuration INSTANCE;
    private String host;
    private int port = -1;
    private volatile String username;
    private volatile String password;

    public static HashMap<String, ResponsePacket> getResponses() {
        return Configuration.responses;
    }

    private static Class<?> getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + "."
                    + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            // handle the exception
        }
        return null;
    }

    public static Configuration getInstance() {
        if (Configuration.INSTANCE == null) {
            Configuration.INSTANCE = new Configuration();
            Configuration.INSTANCE.register();
        }
        return Configuration.INSTANCE;
    }

    public Set<Class<?>> findClasses(String packageName) {
        InputStream stream = ClassLoader.getSystemClassLoader()
                .getResourceAsStream(packageName.replaceAll("[.]", "/"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return reader.lines()
                .filter(line -> line.endsWith(".class"))
                .map(line -> Configuration.getClass(line, packageName))
                .collect(Collectors.toSet());
    }

    public void register() {
        try {
            Set<Class<?>> classes = this.findClasses("api.common.network.packets.response");
            for (Class<?> clazz : classes) {
                ResponsePacket handler;
                try {
                    handler = (ResponsePacket) clazz.newInstance();
                } catch (ClassCastException | InstantiationException e) {
                    continue;
                }
                Configuration.responses.put(handler.getPacketId(), handler);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public String getHost() {
        return host;
    }

    public Configuration setHost(String host) {
        if (this.host != null) return this;
        this.host = host;
        return this;
    }

    public int getPort() {
        return port;
    }

    public Configuration setPort(int port) {
        if (this.port > 0) return this;
        this.port = port;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public Configuration setUsername(String username) {
        if (this.username != null) return this;
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Configuration setPassword(String password) {
        if (this.password != null) return this;
        this.password = password;
        return this;
    }
}
