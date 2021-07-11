import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class Server {
    public static ArrayList<DataOutputStream>users=new ArrayList<>();
    public static void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(7777);
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> {
                    try {
                        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                        users.add(dataOutputStream);
                        while (true) {
                            try {
                                String message = dataInputStream.readUTF();
                                for(DataOutputStream dataOutputStreamUser:users) {
                                    dataOutputStreamUser.writeUTF(message);
                                    dataOutputStreamUser.flush();
                                }
                            } catch (SocketException e) {
                                users.remove(dataOutputStream);
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
    }

}
