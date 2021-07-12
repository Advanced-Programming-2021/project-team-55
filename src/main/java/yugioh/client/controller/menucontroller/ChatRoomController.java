package yugioh.client.controller.menucontroller;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import yugioh.client.model.User;
import yugioh.client.view.NetAdapter;
import yugioh.client.view.SoundPlayable;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ChatRoomController  extends MenuController implements Initializable{
    public TextField message;
    public TextArea chatBox;
    public static Scanner input=new Scanner(System.in);
    public void sendMessage(MouseEvent mouseEvent) throws Exception {
        dataOutputStream.writeUTF("chat "+User.loggedInUser.getNickname()+": "+message.getText());
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
                            if(!inputMessage.startsWith(User.loggedInUser.getNickname())){
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

    public void back(MouseEvent mouseEvent) throws Exception{
        SoundPlayable.playButtonSound("backButton");
        mainMenu.execute();
    }
}
