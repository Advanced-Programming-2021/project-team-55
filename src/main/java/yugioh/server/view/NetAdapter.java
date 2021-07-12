package yugioh.server.view;


import yugioh.server.view.Menus.Menu;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

public class NetAdapter {

    private final int port;
    private ServerSocket serverSocket;
    private DataInputStream dataInputStream;
    private Thread listeningThread;

    public NetAdapter(int port) {
        this.port = port;
        startListening(port);
    }

//    public static void sendMessage(String message, int port, String host) throws IOException {
//        Socket socket = new Socket(host, port);
//        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
//        dataOutputStream.writeUTF(User.getLoggedInUser().getUsername() + " -> " + message);
//        dataOutputStream.flush();
//        dataOutputStream.close();
//        socket.close();
//    }

    private void startListening(int port) {
        listeningThread = new Thread(() -> {
            try {
                serverSocket = new ServerSocket(port);
                while (true) {
                    Socket socket = serverSocket.accept();
                    new Thread(() -> {
                        try {
                            dataInputStream = new DataInputStream(socket.getInputStream());
                            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                            String input = dataInputStream.readUTF();
                            ViewInterface.command = input;
                            String result = Menu.handleCommand(input);
                            dataOutputStream.writeUTF(result);
                            dataOutputStream.flush();
                            dataOutputStream.close();
                            System.out.println("--> " + input);
                            System.out.println(">>> " + result);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }).start();
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
