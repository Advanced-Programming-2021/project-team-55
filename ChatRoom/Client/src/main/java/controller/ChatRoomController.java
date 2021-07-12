package controller;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ChatRoomController implements Initializable {
    private static Socket socket;
    private static DataInputStream dataInputStream;
    private static DataOutputStream dataOutputStream;
    public TextField message;
    public TextArea chatBox;
    public static Scanner input=new Scanner(System.in);
    public static String username;

    public static void setupConnection() {
        try {
            socket = new Socket("localhost", 7777);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            System.out.println("enter your username:");
            username=input.nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void back(MouseEvent mouseEvent) throws Exception {
        dataInputStream.close();
        dataOutputStream.close();
        System.exit(0);
    }

    public void sendMessage(MouseEvent mouseEvent) throws Exception {
        dataOutputStream.writeUTF(username+": "+message.getText());
        message.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chatBox.setWrapText(true);
        chatBox.setEditable(false);
        new Thread(() -> {
            while (true) {
                try {
                    String inputMessage = dataInputStream.readUTF();
                    if (!inputMessage.equals("")) {
                        Platform.runLater(() -> {
                            if(!inputMessage.startsWith(username)){
                                String otherUsername=inputMessage.substring(0,inputMessage.indexOf(":"));
                                String message=inputMessage.substring(inputMessage.indexOf(":")+2);
                                message+=" :"+otherUsername;
                                chatBox.setText(chatBox.getText() + "\n\t\t\t\t" + message);
                            }
                            else
                            chatBox.setText(chatBox.getText() + "\n" + inputMessage);
                        });
                    }
                } catch (IOException e) {
                    try {
                        dataInputStream.close();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
