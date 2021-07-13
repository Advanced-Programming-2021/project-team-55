package yugioh.client.controller.menucontroller;

import com.jfoenix.controls.JFXTextField;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.skin.ScrollPaneSkin;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import yugioh.client.model.User;
import yugioh.client.view.NetAdapter;
import yugioh.client.view.SoundPlayable;
import yugioh.client.view.menus.WelcomeMenu;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ChatRoomController  extends MenuController implements Initializable{
   // public TextArea message;
    public ScrollPane chatBox;
    public transient Thread chatThread;
    public Button sendMessageButton;
    public static Scanner input=new Scanner(System.in);
    public ImageView sendImage;
    public JFXTextField message;
    private boolean isChatEnded=false;


    public void sendMessage(Event event) throws Exception {
        dataOutputStream.writeUTF("chat "+User.loggedInUser.getNickname()+": "+message.getText());
        dataOutputStream.flush();
        message.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       // message.setWrapText(true);
        WelcomeMenu.stage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode()== KeyCode.ENTER){
                    try {
                        sendMessage(event);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        message.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(t1.equals("")){
                    sendMessageButton.setDisable(true);
                }
                else{
                    sendMessageButton.setDisable(false);
                }
            }
        });
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
                           // String message=inputMessage.substring(inputMessage.indexOf(":")+2);
//                            message+=" :"+otherUsername;
                          //  chatBox.setText(chatBox.getText() + "\n\t\t\t\t" + inputMessage);
                            AnchorPane anchorPane=(AnchorPane) chatBox.getContent();
                            double yLastMessage;
                            if(anchorPane.getChildren().size()>0) {
                                Label lastMessage = (Label) anchorPane.getChildren().get(anchorPane.getChildren().size()-1);
                                yLastMessage = lastMessage.getLayoutY();
                            }
                            else {
                                yLastMessage = -10;
                            }
                            Label messageLabel=new Label(inputMessage);

                            messageLabel.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY,null)));
                            messageLabel.setTextFill(Color.CHARTREUSE);
                            messageLabel.setLayoutY(yLastMessage+30);
                            messageLabel.setLayoutX(100);
                            anchorPane.getChildren().add(messageLabel);
                            chatBox.setContent(anchorPane);

                            //othersChatBox.getChildren().add(messageLabel);
                            //chatPane.setContent(new Rectangle());

                        }
                        else {
                            AnchorPane anchorPane=(AnchorPane) chatBox.getContent();
                            double yLastMessage;
                            if(anchorPane.getChildren().size()>0) {
                                Label lastMessage = (Label) anchorPane.getChildren().get(anchorPane.getChildren().size()-1);
                                yLastMessage = lastMessage.getLayoutY();
                            }
                            else {
                                yLastMessage = -10;
                            }
                            Label messageLabel=new Label(inputMessage);
                            messageLabel.setTextFill(Color.RED);
                            messageLabel.setBackground(new Background(new BackgroundFill(Color.CHARTREUSE, CornerRadii.EMPTY,null)));
                            messageLabel.setLayoutY(yLastMessage+30);
                            messageLabel.setLayoutX(0);
                            anchorPane.getChildren().add(messageLabel);
                            chatBox.setContent(anchorPane);
                           // chatBox.setText(chatBox.getText() + "\n" + inputMessage);
                           // Label messageLabel=new Label(inputMessage);
                            //messageLabel.setBackground(new Background(new BackgroundFill(Color.CHARTREUSE, CornerRadii.EMPTY,null)));
                            //chatPane.getChildren().add(messageLabel);
                            //myChatBox.getChildren().add(messageLabel);
                        }
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
    public void setHoveredImageSend() {
        sendImage.setImage(new Image(new File("src/resources/yugioh/PNG/Field/sendHover.png").toURI().toString()));
    }

    public void resetHoveredImageSend() {
        sendImage.setImage(new Image(new File("src/resources/yugioh/PNG/Field/send.png").toURI().toString()));
    }
}
