package yugioh.client.view;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

public class NetAdapter {

    private static String host = "127.0.0.1";
    private static int port = 4444;

    private static Socket socket;
    private static DataOutputStream dataOutputStream;
    private static DataInputStream dataInputStream;

    public static void initialize() {
        try {
            socket = new Socket(host, port);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("failed to connect to server");
        }
    }

    public static String sendRequest(String message) {
        try {
            dataOutputStream.writeUTF(message);
            dataOutputStream.flush();
            String result = dataInputStream.readUTF();
            dataOutputStream.close();
            socket.close();
            return result;
        } catch (Exception e) {
            return "Error: failed to send request";
        }
    }

    public static void setHost(String host) {
        NetAdapter.host = host;
    }

    public static void setPort(int port) {
        NetAdapter.port = port;
    }

}