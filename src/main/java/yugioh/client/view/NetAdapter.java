package yugioh.client.view;

import yugioh.client.model.User;
import yugioh.client.view.gamephases.Duel;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;

public class NetAdapter {

    public static String host = "127.0.0.1";
    private static int port = 3333;

    public static Socket socket;
    public static DataOutputStream dataOutputStream;
    public static DataInputStream dataInputStream;
    public static DataOutputStream dataOutputStreamForSaving;
    public static DataOutputStream dataOutputStreamForChat;
    public static DataInputStream dataInputStreamForChat;
    public static DataInputStream dataInputStreamForGettingOnlineUsere;
    public static DataOutputStream dataOutputStreamForGettingOnlineUsers;

//    public static Socket tvSocket;
//    public static DataOutputStream tvDataOutputStream;

    public static void initialize() {
       try {
           socket = new Socket(host, port);
           dataOutputStream = new DataOutputStream(socket.getOutputStream());
           dataInputStream = new DataInputStream(socket.getInputStream());
          Socket socketForSaving=new Socket(host,3334);
          dataOutputStreamForSaving=new DataOutputStream(socketForSaving.getOutputStream());
          Socket socketForChatting=new Socket(host,3335);
          dataOutputStreamForChat=new DataOutputStream(socketForChatting.getOutputStream());
          dataInputStreamForChat=new DataInputStream(socketForChatting.getInputStream());
          Socket socketForGettingOnlineUsers=new Socket(host,3336);
          dataInputStreamForGettingOnlineUsere=new DataInputStream(socketForGettingOnlineUsers.getInputStream());
          dataOutputStreamForGettingOnlineUsers=new DataOutputStream(socketForGettingOnlineUsers.getOutputStream());


//           tvSocket = new Socket(host, 9595);
//           tvDataOutputStream = new DataOutputStream(tvSocket.getOutputStream());

       } catch (IOException e) {
           System.out.println("failed to connect to server");
       }
    }

    public static String sendRequest(String message) {
        try {
//            socket = new Socket(host, port);
//            dataOutputStream = new DataOutputStream(socket.getOutputStream());
//            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream.writeUTF(message);
            String result = dataInputStream.readUTF();
            dataOutputStream.flush();
//            dataOutputStream.close();
//            dataInputStream.close();
//            socket.close();
            return result;
        } catch (Exception e) {
            return "Error: failed to send request";
        }
    }

    public static void justSendRequest(String message) {
        try {
            dataOutputStream.writeUTF(message);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendForwardRequest(String message) {
        try {
            dataOutputStream.writeUTF("forward: " + message);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendForwardRequestForGame(String message) {
        try {
            if (!Duel.getGameController().currentTurnPlayer.getUser().equals(User.getLoggedInUser())) return;
            dataOutputStream.writeUTF("forward: " + message);
            dataOutputStream.flush();
            System.out.println("request sent to server in send forwardRequestForGame method: "+message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setHost(String host) {
        NetAdapter.host = host;
    }

    public static void setPort(int port) {
        NetAdapter.port = port;
    }

}
