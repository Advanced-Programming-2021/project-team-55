package yugioh.client.controller.menucontroller;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.skin.ScrollPaneSkin;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import yugioh.client.model.User;
import yugioh.client.view.NetAdapter;
import yugioh.client.view.SoundPlayable;
import yugioh.client.view.menus.ChatRoom;
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
    public static Scanner input=new Scanner(System.in);
    public ImageView sendImage;
    public JFXTextField message;
    public ImageView backImage;
    public Label replyMessage;
    public ImageView cancelReply;
    private boolean isChatEnded=false;


    public void sendMessage(Event event) throws Exception {
        if(replyMessage.getText().equals("")) {
            dataOutputStreamForChat.writeUTF("chat " + User.loggedInUser.getNickname() + ": " + message.getText());
        }
        else{
            String lastReplyMessage;
            if(replyMessage.getText().contains("\n"))lastReplyMessage=replyMessage.getText().substring(0,replyMessage.getText().indexOf("\n"));
            else lastReplyMessage=replyMessage.getText();
            dataOutputStreamForChat.writeUTF("chat "+User.loggedInUser.getNickname()+": "+message.getText()+"\nreplied to "+
                    lastReplyMessage);
        }
        cancelReply((MouseEvent) event);
        dataOutputStreamForChat.flush();
        message.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sendImage.setDisable(true);
        sendImage.setOpacity(0.5);
        cancelReply.setOpacity(0.2);
        cancelReply.setDisable(true);
        AnchorPane pane=(AnchorPane)chatBox.getContent();
        if(pane.getChildren().size()!=0){
            double previousY=0;
            int counter=0;
            for(Node label:pane.getChildren()){
                if(counter++==0){
                    previousY=label.getLayoutY();
                    continue;
                }
                label.setLayoutY(previousY+20);
                previousY=label.getLayoutY();
            }
        }
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
                    sendImage.setDisable(true);
                    sendImage.setOpacity(0.5);
                }
                else{
                    sendImage.setDisable(false);
                    sendImage.setOpacity(1);
                }
            }
        });
        chatThread =new Thread(() -> {
        while (true) {
            try {
                String inputMessage = dataInputStreamForChat.readUTF();
                if (inputMessage.equals(User.loggedInUser.getUsername() + " gomsho")) {
                    isChatEnded=true;
                    return;
                }
                if (!inputMessage.equals("")) {
                    Platform.runLater(() -> {
                        if(!inputMessage.startsWith(User.loggedInUser.getNickname())){
                            AnchorPane anchorPane=(AnchorPane) chatBox.getContent();
                            double yLastMessage;
                            if(anchorPane.getChildren().size()>0) {
                                Label lastMessage = (Label) anchorPane.getChildren().get(anchorPane.getChildren().size()-1);
                                yLastMessage = lastMessage.getLayoutY()+lastMessage.getHeight();
                            }
                            else {
                                yLastMessage = -10;
                            }
                            Label messageLabel=new Label(inputMessage);

                            messageLabel.setOnDragDetected(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent mouseEvent) {
                                    TranslateTransition translateTransition=new TranslateTransition();
                                    translateTransition.setByX(-100);
                                    translateTransition.setNode(messageLabel);
                                    translateTransition.setDuration(Duration.seconds(0.5));
                                    translateTransition.play();
                                    translateTransition.setOnFinished(new EventHandler<ActionEvent>() {
                                        @Override
                                        public void handle(ActionEvent actionEvent) {
                                            TranslateTransition translateTransition1=new TranslateTransition();
                                            translateTransition1.setByX(100);
                                            translateTransition1.setNode(messageLabel);
                                            translateTransition1.setDuration(Duration.seconds(0.5));
                                            translateTransition1.play();
                                            translateTransition1.setOnFinished(new EventHandler<ActionEvent>() {
                                                @Override
                                                public void handle(ActionEvent actionEvent) {
                                                    replyMessage.setText(messageLabel.getText());
                                                    replyMessage.setBackground(new Background(new BackgroundFill(Color.CYAN,CornerRadii.EMPTY,null)));
                                                    cancelReply.setDisable(false);
                                                    cancelReply.setOpacity(1);
                                                }
                                            });
                                        }
                                    });
                                }
                            });


                            messageLabel.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY,null)));
                            messageLabel.setTextFill(Color.CHARTREUSE);
                            messageLabel.setLayoutY(yLastMessage+20);
                            messageLabel.setLayoutX(400);
                            anchorPane.getChildren().add(messageLabel);
                            chatBox.setContent(anchorPane);

                        }
                        else {
                            AnchorPane anchorPane=(AnchorPane) chatBox.getContent();
                            double yLastMessage;
                            if(anchorPane.getChildren().size()>0) {
                                Label lastMessage = (Label) anchorPane.getChildren().get(anchorPane.getChildren().size()-1);
                                yLastMessage = lastMessage.getLayoutY()+lastMessage.getHeight();
                            }
                            else {
                                yLastMessage = -10;
                            }
                            Label messageLabel=new Label(inputMessage);

                            messageLabel.setOnDragDetected(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent mouseEvent) {
                                    TranslateTransition translateTransition=new TranslateTransition();
                                    translateTransition.setByX(100);
                                    translateTransition.setNode(messageLabel);
                                    translateTransition.setDuration(Duration.seconds(0.5));
                                    translateTransition.play();
                                    translateTransition.setOnFinished(new EventHandler<ActionEvent>() {
                                        @Override
                                        public void handle(ActionEvent actionEvent) {
                                            TranslateTransition translateTransition1=new TranslateTransition();
                                            translateTransition1.setToX(0);
                                            translateTransition1.setNode(messageLabel);
                                            translateTransition1.setDuration(Duration.seconds(0.5));
                                            translateTransition1.play();
                                            translateTransition1.setOnFinished(new EventHandler<ActionEvent>() {
                                                @Override
                                                public void handle(ActionEvent actionEvent) {
                                                    replyMessage.setText(messageLabel.getText());
                                                    replyMessage.setBackground(new Background(new BackgroundFill(Color.CYAN,CornerRadii.EMPTY,null)));
                                                    cancelReply.setDisable(false);
                                                    cancelReply.setOpacity(1);
                                                }
                                            });
                                        }
                                    });
                                }
                            });

//                            messageLabel.setOnDragExited(new EventHandler<DragEvent>() {
//                                @Override
//                                public void handle(DragEvent dragEvent) {
//                                    messageLabel.setLayoutX(0);
//                                }
//                            });

                            messageLabel.setTextFill(Color.RED);
                            messageLabel.setBackground(new Background(new BackgroundFill(Color.CHARTREUSE, CornerRadii.EMPTY,null)));
                            messageLabel.setLayoutY(yLastMessage+20);
                            messageLabel.setLayoutX(0);
                            anchorPane.getChildren().add(messageLabel);
                            chatBox.setContent(anchorPane);


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
        dataOutputStreamForChat.writeUTF(User.loggedInUser.getUsername() + " exited Chatroom");
        dataOutputStreamForChat.flush();
        SoundPlayable.playButtonSound("backButton");
        chatThread.stop();
        ChatRoom.close();
        new Thread(()->{
            while (!isChatEnded){ }
                Platform.runLater(()->{
                    try {
                        ChatRoom.close();
                    } catch (Exception e) {
                    }
                });
        }).start();
    }
    public void setHoveredImageSend() {
        sendImage.setImage(new Image(new File("src/resources/yugioh/PNG/icon/sendHover.png").toURI().toString()));
    }

    public void resetHoveredImageSend() {
        sendImage.setImage(new Image(new File("src/resources/yugioh/PNG/icon/send.png").toURI().toString()));
    }

    public void resetHoveredImageBack(MouseEvent mouseEvent) {
        backImage.setImage(new Image(new File("src/resources/yugioh/PNG/icon/close.png").toURI().toString()));
    }

    public void setHoveredImageBack(MouseEvent mouseEvent) {
        backImage.setImage(new Image(new File("src/resources/yugioh/PNG/icon/closeHover.png").toURI().toString()));
    }

    public void cancelReply(MouseEvent mouseEvent) {
        replyMessage.setText("");
        replyMessage.setBackground(null);
        cancelReply.setDisable(true);
        cancelReply.setOpacity(0.2);
    }
}
