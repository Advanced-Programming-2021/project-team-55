package yugioh.server.view;


import yugioh.server.controller.DataBaseController;
import yugioh.server.model.UserHolder;
import yugioh.server.view.Menus.Menu;
import yugioh.server.view.gamephases.Duel;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.regex.Matcher;

import static yugioh.server.view.ViewInterface.getCommandMatcher;

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
        new Thread(()->{
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> {
                    UserHolder userHolder = new UserHolder();
                    try {
                        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                        userHolder.setDataInputStream(dataInputStream);
                        userHolder.setDataOutputStream(dataOutputStream);
                        while (true) {
                            try {
                                String input = dataInputStream.readUTF();
                                ViewInterface.command = input;
                                sendToRival(input, userHolder);
                                String result = Menu.handleCommand(input, userHolder);
                                    dataOutputStream.writeUTF(result);
                                    dataOutputStream.flush();
                                log(input, result);
                            } catch (SocketException e) {
                                allUsersOutputStreams.remove(dataOutputStream);
                                if (logUserDisconnection(userHolder, e)) return;
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
        new Thread(()->{
            try {
                ServerSocket serverSocket = new ServerSocket(12345);
                while (true) {
                    Socket socket = serverSocket.accept();
                    new Thread(() -> {
                        try {
                            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                            while (true) {
                                try {
                                    String input = dataInputStream.readUTF();
                                    Matcher matcher=getCommandMatcher(input,"save user address: (.*) content: (.*)");
                                    String address=matcher.group(1);
                                    String content=matcher.group(2);

                                        DataBaseController.updateUserInfo(address,content);
                                } catch (SocketException e) {
                                    if (e.getMessage().contains("Connection reset")) {
                                        System.out.print("a client disconnected: ");
                                        try {
                                        } catch (Exception ignored) {
                                        }
                                        return;
                                    }
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
        new Thread(()->{
            try {
                ServerSocket serverSocket = new ServerSocket(11111);
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
                                    if(input.matches(Regexes.EXIT_CHATROOM.regex)){
                                        Matcher matcher = getCommandMatcher(input, Regexes.EXIT_CHATROOM.regex);
                                        System.out.println(input);
                                        dataOutputStream.writeUTF(matcher.group(1) + " gomsho");
                                        dataOutputStream.flush();
                                    }
                                    else {
                                        for (DataOutputStream dataOutputStreamUser : allUsersOutputStreams) {
                                            dataOutputStreamUser.writeUTF(input.replace("chat ", ""));
                                            dataOutputStreamUser.flush();
                                        }
                                    }
//                                    System.out.println("--> " + input);
//                                    System.out.println(">>> " + input);
                                } catch (SocketException e) {
                                    allUsersOutputStreams.remove(dataOutputStream);
                                    if (e.getMessage().contains("Connection reset")) {
                                       // System.out.print("a client disconnected: ");
                                        try {
                                            //System.out.println(userHolder.getUser().getUsername());
                                        } catch (Exception ignored) {
                                        }
                                        return;
                                    }
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
    }

    private boolean logUserDisconnection(UserHolder userHolder, SocketException e) {
        if (e.getMessage().contains("Connection reset")) {
            try {
                System.out.println("a client disconnected: " + userHolder.getUser().getUsername());
            } catch (Exception ignored) {
                System.out.print("a client disconnected.");
            }
            return true;
        }
        return false;
    }

    private void sendToRival(String input, UserHolder userHolder) {
        if (input.startsWith("forward: ")) {
            if (Duel.getGamesInProgress().containsKey(userHolder)) {
                try {
                    Duel.getGamesInProgress().get(userHolder).getDataOutputStream().writeUTF(input);
                    Duel.getGamesInProgress().get(userHolder).getDataOutputStream().flush();
                    System.out.println("a command forwarded: " + input.replaceAll("forward: ", ""));
                } catch (IOException ignored) {
                }
            }
        }
    }

    private void log(String input, String result) {
        System.out.println("--> " + input);
        System.out.println(">>> " + result);
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
