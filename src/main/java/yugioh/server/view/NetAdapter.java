package yugioh.server.view;


import yugioh.server.model.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

public class NetAdapter {

    private final int port;
    private final User owner;
    private ServerSocket serverSocket;
    private DataInputStream dataInputStream;
    private Thread listeningThread;

    public NetAdapter(int port) {
        this.port = port;
        startListening(port);
        owner = User.getLoggedInUser();
    }

    public static void sendMessage(String message, int port, String host) throws IOException {
        Socket socket = new Socket(host, port);
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeUTF(User.getLoggedInUser().getUsername() + " -> " + message);
        dataOutputStream.flush();
        dataOutputStream.close();
        socket.close();
    }

    private void startListening(int port) {
        listeningThread = new Thread(() -> {
            try {
                SocketAddress socketAddress = new InetSocketAddress(InetAddress.getLoopbackAddress(), port);
                serverSocket = new ServerSocket();
                serverSocket.bind(socketAddress);
                while (true) {
                    Socket socket = serverSocket.accept();
                    dataInputStream = new DataInputStream(socket.getInputStream());
                    String input = dataInputStream.readUTF();
                    System.out.println(input);
                }
            } catch (IOException ignored) {
            }
        });
        listeningThread.start();
    }

    public int getPort() {
        return port;
    }

    public void close() {
        try {
            listeningThread.interrupt();
            serverSocket.close();
            dataInputStream.close();
        } catch (Exception ignored) {
        }
    }

}
