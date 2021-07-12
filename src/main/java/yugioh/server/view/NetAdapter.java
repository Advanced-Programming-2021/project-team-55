package yugioh.server.view;


import yugioh.server.view.Menus.Menu;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class NetAdapter {

    private final int port;
    public ArrayList<DataOutputStream> allUsersOutputStreams = new ArrayList<>();
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
//        listeningThread = new Thread(() -> {
//            try {
//                SocketAddress socketAddress = new InetSocketAddress(InetAddress.getLoopbackAddress(), port);
//                serverSocket = new ServerSocket();
//                serverSocket.bind(socketAddress);
//                while (true) {
//                    Socket socket = serverSocket.accept();
//                    new Thread(() -> {
//                        try {
//                            dataInputStream = new DataInputStream(socket.getInputStream());
//                            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
//                            allUsersOutputStreams.add(dataOutputStream);
//                            String input = dataInputStream.readUTF();
//                            ViewInterface.command = input;
//                            String result = Menu.handleCommand(input);
//                            if(result.startsWith("chat ")){
//                                for(DataOutputStream dataOutputStreamUser:allUsersOutputStreams){
//                                    dataOutputStreamUser.writeUTF(result.replace("chat ",""));
//                                }
//                            }
//                            else {
//                                dataOutputStream.writeUTF(result);
//                            }
//                            dataOutputStream.flush();
//                            dataOutputStream.close();
//                            System.out.println("--> " + input);
//                            System.out.println(">>> " + result);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }).start();
//                }
//            } catch (IOException ignored) {
//            }
//        });
        //  listeningThread.start();
        new Thread(()->{
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> {
                    try {
                        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                        allUsersOutputStreams.add(dataOutputStream);
                        while (true) {
                            try {
                                String input = dataInputStream.readUTF();
                                ViewInterface.command = input;
                                String result = Menu.handleCommand(input);
                                if (result.startsWith("chat ")) {
                                    for (DataOutputStream dataOutputStreamUser : allUsersOutputStreams) {
                                        dataOutputStreamUser.writeUTF(result.replace("chat ", ""));
                                    }
                                } else {
                                    dataOutputStream.writeUTF(result);
                                }
                                System.out.println("--> " + input);
                                System.out.println(">>> " + result);
                            } catch (SocketException e) {
                                allUsersOutputStreams.remove(dataOutputStream);
                                e.printStackTrace();
                                break;
                            }
                        }
                        dataInputStream.close();
                        dataOutputStream.close();
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
            }).start();
//        new Thread(()->{
//
//
//        try {
//            ServerSocket serverSocket = new ServerSocket(12345);
//            while (true) {
//                Socket socket = serverSocket.accept();
//                new Thread(() -> {
//                    try {
//                        DataInputStream dataInputStreamForChat = new DataInputStream(socket.getInputStream());
//                        DataOutputStream dataOutputStreamForChat = new DataOutputStream(socket.getOutputStream());
//                        allUsersOutputStreams.add(dataOutputStreamForChat);
//                        while (true) {
//                            try {
//                                String message= dataInputStreamForChat.readUTF();
//                                    for (DataOutputStream dataOutputStreamUser : allUsersOutputStreams) {
//                                        dataOutputStreamUser.writeUTF(message);
//                                    }
//
//                            } catch (SocketException e) {
//                                allUsersOutputStreams.remove(dataOutputStreamForChat);
//                                e.printStackTrace();
//                                break;
//                            }
//                        }
//                        dataInputStream.close();
//                        dataOutputStreamForChat.close();
//                        socket.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }).start();
//
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        }).start();
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
