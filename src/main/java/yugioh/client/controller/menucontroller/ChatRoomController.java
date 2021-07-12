package yugioh.client.controller.menucontroller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import yugioh.client.model.User;
import yugioh.client.view.NetAdapter;
import yugioh.client.view.SoundPlayable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ChatRoomController  extends MenuController implements Initializable{
    public TextField message;
    public TextArea chatBox;
    public transient Thread chatThread;
    public static Scanner input=new Scanner(System.in);
    private boolean isChatEnded=false;

    public void sendMessage(MouseEvent mouseEvent) throws Exception {
        dataOutputStream.writeUTF("chat "+User.loggedInUser.getNickname()+": "+message.getText());
        dataOutputStream.flush();
        message.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chatBox.setWrapText(true);
        chatBox.setEditable(false);
        chatThread =new Thread(() -> {
        while (true) {
            try {
                String inputMessage = dataInputStream.readUTF();
                if (inputMessage.equals(User.loggedInUser.getUsername() + " gomsho")) {
                    isChatEnded=true;
                    return;
                }
                if (!inputMessage.equals("")) {
                    Platform.runLater(() -> {
                        if(!inputMessage.startsWith(User.loggedInUser.getNickname())){
//                            String otherUsername=inputMessage.substring(0,inputMessage.indexOf(":"));
                            String message=inputMessage.substring(inputMessage.indexOf(":")+2);
//                            message+=" :"+otherUsername;
                            chatBox.setText(chatBox.getText() + "\n\t\t\t\t" + message);
                        }
                        else
                            chatBox.setText(chatBox.getText() + "\n" + inputMessage);
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });
        chatThread.start();
    }

    public void back(MouseEvent mouseEvent) throws Exception{
        NetAdapter.justSendRequest(User.loggedInUser.getUsername() + " exited Chatroom");
        SoundPlayable.playButtonSound("backButton");
        chatThread.stop();
        new Thread(()->{
            while (!isChatEnded){ }
                Platform.runLater(()->{
                    try {
                        mainMenu.execute();
                    } catch (Exception e) {
                    }
                });
        }).start();
//        new Timeline(new KeyFrame(Duration.seconds(2),actionEvent ->{
//                try{
//                    mainMenu.execute();
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//        })).play();
        //mainMenu.execute();
    }
}
