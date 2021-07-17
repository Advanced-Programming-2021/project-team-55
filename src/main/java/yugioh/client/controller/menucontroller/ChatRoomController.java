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
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.skin.ScrollPaneSkin;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
import java.util.regex.Matcher;

import static javafx.scene.paint.Color.GREEN;
import static yugioh.client.view.ViewInterface.getCommandMatcher;

public class ChatRoomController  extends MenuController implements Initializable{
   // public TextArea message;
    public AnchorPane chatPane;
    public ScrollPane chatBox;
    public transient Thread chatThread;
    public static Scanner input=new Scanner(System.in);
    public ImageView sendImage;
    public JFXTextField message;
    public ImageView backImage;
    public Label replyMessage;
    public ImageView cancelReply;
    public Label selectedMessage;
    public ImageView deleteImage;
    public ImageView editImage;
    public ImageView pinImage;
    public Label onlineUsers;
    public ImageView unpinImage;
    {
        unpinImage=new ImageView(new Image(new File("src\\resources\\yugioh\\PNG\\icon\\close.png").toURI().toString()));
        unpinImage.setLayoutX(600);
        unpinImage.setLayoutY(32);
        unpinImage.setFitWidth(32);
        unpinImage.setFitHeight(30);
        unpinImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                unpinMessage();
            }
        });
        unpinImage.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                unpinImage.setImage(new Image(new File("src/resources/yugioh/PNG/icon/closeHover.png").toURI().toString()));
            }
        });
        unpinImage.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                unpinImage.setImage(new Image(new File("src/resources/yugioh/PNG/icon/close.png").toURI().toString()));
            }
        });
    }
    public Label pinnedMessage=new Label();
    {
        pinnedMessage.setLayoutX(23);
        pinnedMessage.setLayoutY(32);
        pinnedMessage.setPrefWidth(635);
        pinnedMessage.setPrefHeight(34);
        pinnedMessage.setBackground(new Background(new BackgroundFill(Color.CYAN,CornerRadii.EMPTY,null)));
        pinnedMessage.styleProperty().set("-fx-font-family: \"Bauhaus 93\";-fx-font-size: 20px;");
    }
    private boolean isChatEnded=false;
    private boolean shouldEditMessage=false;
    public static boolean hasEnteredChatMenu=false;


    public void sendMessage(Event event) throws Exception {
        if(shouldEditMessage){
            dataOutputStreamForChat.writeUTF("edit message: "+User.loggedInUser.getNickname()+": "+replyMessage.getText()
                    +" to: "+User .loggedInUser.getNickname()+": "+message.getText());
            dataOutputStreamForChat.flush();
            cancelReply((MouseEvent) event);
        }
        else {
            if (replyMessage.getText().equals("")) {
                dataOutputStreamForChat.writeUTF("chat " + "image: " + User.loggedInUser.getProfileImageString() +
                        " message: " + User.loggedInUser.getNickname() + ": " + message.getText());
            } else {
                String lastReplyMessage;
                if (replyMessage.getText().contains("\n"))
                    lastReplyMessage = replyMessage.getText().substring(0, replyMessage.getText().indexOf("\n"));
                else lastReplyMessage = replyMessage.getText();
                dataOutputStreamForChat.writeUTF("chat " + "image: " + User.loggedInUser.getProfileImageString() +
                        " message: " + User.loggedInUser.getNickname() + ": " + message.getText() + "\nreplied to " +
                        lastReplyMessage);
            }
            cancelReply((MouseEvent) event);
            dataOutputStreamForChat.flush();
            message.setText("");
        }
    }
    private void updateOnlineUsersCount(){
        new Thread(()->{
            while (true){
                try {
                    dataOutputStreamForGettingOnlineUsers.writeUTF("get online users");
                    dataOutputStreamForGettingOnlineUsers.flush();
                    String count = dataInputStreamForGettingOnlineUsers.readUTF();
                    Platform.runLater(() -> {
                        onlineUsers.setText("Online: "+ count);
                    });
                }catch (Exception e){}
                try {
                    Thread.sleep(10000);
                }catch (Exception e){}
            }
        }).start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateOnlineUsersCount();
        sendImage.setDisable(true);
        sendImage.setOpacity(0.5);
        cancelReply.setOpacity(0.2);
        cancelReply.setDisable(true);
        disableDeleteImage(true);
        disableEditImage(true);
        disablePinImage(true);
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
                String inputMessageInfo = dataInputStreamForChat.readUTF();
                if (inputMessageInfo.equals(User.loggedInUser.getUsername() + " gomsho")) {
                    isChatEnded=true;
                    return;
                }
                if (!inputMessageInfo.equals("")&&hasEnteredChatMenu) {
                    if(inputMessageInfo.startsWith("delete ")){
                        Matcher matcher=getCommandMatcher(inputMessageInfo,"delete message: ([\\w\\d\\s\\n:,.?!@#$%^&*()-+_=]+)");
                        deleteMessage(matcher.group(1));
                    }
                    else if(inputMessageInfo.startsWith("edit ")){
                        Matcher matcher=getCommandMatcher(inputMessageInfo,"edit message: ([\\w\\d\\s\\n:,.?!@#$%^&*()-+_=]+) to: ([\\w\\d\\s\\n:,.?!@#$%^&*()-+_=]+)");
                        editMessage(matcher.group(1),matcher.group(2));
                    }
                    else if(inputMessageInfo.startsWith("pin ")){
                        Matcher matcher=getCommandMatcher(inputMessageInfo,"pin message: ([\\w\\d\\s\\n:,.?!@#$%^&*()-+_=]+)");
                        pinMessage(matcher.group(1));
                    }
                    else if(inputMessageInfo.startsWith("unpin")){
                        unpinMessageForAll();
                    }
                    else {
                        Matcher matcher = getCommandMatcher(inputMessageInfo, "chat image: (.*) message: ([\\w\\d\\s\\n:,.?!@#$%^&*()-+_=]+)");
                        String inputMessage = matcher.group(2);
                        String imageAddress = "src/resources" + matcher.group(1);
                    Platform.runLater(() -> {
                        if(!inputMessage.startsWith(User.loggedInUser.getNickname())){
                            AnchorPane anchorPane=(AnchorPane) chatBox.getContent();
                            double yLastMessage;
                            if(anchorPane.getChildren().size()>0) {
                                Group lastMessage = (Group) anchorPane.getChildren().get(anchorPane.getChildren().size()-1);
                                yLastMessage = lastMessage.getLayoutY()+((Label)lastMessage.getChildren().get(1)).getHeight();
                            }
                            else {
                                yLastMessage = -10;
                            }
                            Label messageLabel=new Label(inputMessage);
                            Rectangle rectangle=new Rectangle(30,30);
                            rectangle.setFill(new ImagePattern(new Image(new File(imageAddress).toURI().toString())));
                            rectangle.setLayoutX(400);
                            rectangle.setLayoutY(yLastMessage+10);
                            Group message=new Group();
                            message.setLayoutY(yLastMessage+5);
                            message.getChildren().add(rectangle);
                            message.getChildren().add(messageLabel);
                            message.setOnDragDetected(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent mouseEvent) {
                                    if (!shouldEditMessage) {
                                        TranslateTransition translateTransition = new TranslateTransition();
                                        translateTransition.setByX(-100);
                                        translateTransition.setNode(message);
                                        translateTransition.setDuration(Duration.seconds(0.5));
                                        translateTransition.play();
                                        translateTransition.setOnFinished(new EventHandler<ActionEvent>() {
                                            @Override
                                            public void handle(ActionEvent actionEvent) {
                                                TranslateTransition translateTransition1 = new TranslateTransition();
                                                translateTransition1.setByX(100);
                                                translateTransition1.setNode(message);
                                                translateTransition1.setDuration(Duration.seconds(0.5));
                                                translateTransition1.play();
                                                translateTransition1.setOnFinished(new EventHandler<ActionEvent>() {
                                                    @Override
                                                    public void handle(ActionEvent actionEvent) {
                                                        replyMessage.setText(messageLabel.getText());
                                                        replyMessage.setTextFill(Color.ORANGE);
                                                        replyMessage.setBackground(new Background(new BackgroundFill(Color.CYAN, CornerRadii.EMPTY, null)));
                                                        cancelReply.setDisable(false);
                                                        cancelReply.setOpacity(1);
                                                    }
                                                });
                                            }
                                        });
                                    }
                                }
                            });
                            messageLabel.setTextFill(Color.CHARTREUSE);
                            messageLabel.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY,null)));
                            messageLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent mouseEvent) {
                                    if(!shouldEditMessage) {
                                        if (messageLabel.getEffect() != null) {
                                            deselectMessage();
                                        } else {
                                            selectMessage(messageLabel);
                                        }
                                    }
                                }
                            });
                            messageLabel.setLayoutY(yLastMessage+40);
                            messageLabel.setLayoutX(400);
                            anchorPane.getChildren().add(message);
                            chatBox.setContent(anchorPane);
                        }
                        else {
                            AnchorPane anchorPane=(AnchorPane) chatBox.getContent();
                            double yLastMessage;
                            if(anchorPane.getChildren().size()>0) {
                                Group lastMessage = (Group) anchorPane.getChildren().get(anchorPane.getChildren().size()-1);
                               yLastMessage = lastMessage.getLayoutY()+((Label)lastMessage.getChildren().get(1)).getHeight();
                            }
                            else {
                                yLastMessage = -10;
                            }
                            Label messageLabel=new Label(inputMessage);
                            Rectangle rectangle=new Rectangle(30,30);
                            rectangle.setFill(new ImagePattern(new Image(new File(imageAddress).toURI().toString())));
                            rectangle.setLayoutX(0);
                            rectangle.setLayoutY(yLastMessage+10);
                            Group message=new Group();
                            message.setLayoutY(yLastMessage+5);
                            message.getChildren().add(rectangle);
                            message.getChildren().add(messageLabel);
                            message.setOnDragDetected(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent mouseEvent) {
                                    if (!shouldEditMessage) {
                                        TranslateTransition translateTransition = new TranslateTransition();
                                        translateTransition.setByX(100);
                                        translateTransition.setNode(message);
                                        translateTransition.setDuration(Duration.seconds(0.5));
                                        translateTransition.play();
                                        translateTransition.setOnFinished(new EventHandler<ActionEvent>() {
                                            @Override
                                            public void handle(ActionEvent actionEvent) {
                                                TranslateTransition translateTransition1 = new TranslateTransition();
                                                translateTransition1.setToX(0);
                                                translateTransition1.setNode(message);
                                                translateTransition1.setDuration(Duration.seconds(0.5));
                                                translateTransition1.play();
                                                translateTransition1.setOnFinished(new EventHandler<ActionEvent>() {
                                                    @Override
                                                    public void handle(ActionEvent actionEvent) {
                                                        replyMessage.setText(messageLabel.getText());
                                                        replyMessage.setTextFill(Color.ORANGE);
                                                        replyMessage.setBackground(new Background(new BackgroundFill(Color.CYAN, CornerRadii.EMPTY, null)));
                                                        cancelReply.setDisable(false);
                                                        cancelReply.setOpacity(1);
                                                    }
                                                });
                                            }
                                        });
                                    }
                                }
                            });
                            messageLabel.setTextFill(Color.RED);
                            messageLabel.setBackground(new Background(new BackgroundFill(Color.CHARTREUSE, CornerRadii.EMPTY,null)));
                            messageLabel.setLayoutY(yLastMessage+40);
                            messageLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent mouseEvent) {
                                    if(!shouldEditMessage) {
                                        if (messageLabel.getEffect() != null) {
                                            deselectMessage();
                                        } else {
                                            selectMessage(messageLabel);
                                        }
                                    }
                                }
                            });
                            messageLabel.setLayoutX(0);
                            anchorPane.getChildren().add(message);
                            chatBox.setContent(anchorPane);
                        }
                    });
                }
            }} catch (IOException e) {
                //e.printStackTrace();
            }
        }
    });
        chatThread.start();
    }

    public void back(MouseEvent mouseEvent) throws Exception{
        dataOutputStreamForChat.writeUTF(User.loggedInUser.getUsername() + " exited Chatroom");
        dataOutputStreamForChat.flush();
        hasEnteredChatMenu=false;
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
        if(shouldEditMessage){
            deselectMessage();
            message.setText("");
            shouldEditMessage=false;
        }
    }

    public void setHoveredImageBackForReply(MouseEvent mouseEvent) {
        cancelReply.setImage(new Image(new File("src/resources/yugioh/PNG/icon/closeHover.png").toURI().toString()));
    }

    public void resetHoveredImageBackForReply(MouseEvent mouseEvent) {
        cancelReply.setImage(new Image(new File("src/resources/yugioh/PNG/icon/close.png").toURI().toString()));
    }

    public void deleteMessage(MouseEvent mouseEvent) {
        if(selectedMessage.getText().startsWith(User.loggedInUser.getNickname())) {
            try {
                dataOutputStreamForChat.writeUTF("delete message: " + selectedMessage.getText());
                dataOutputStreamForChat.flush();
            }catch (Exception e){}
            deselectMessage();
        }else{
            deleteMessage(selectedMessage.getText());
        }
    }
    public void deleteMessage(String message){
        Platform.runLater(()->{
            AnchorPane anchorPane=((AnchorPane)chatBox.getContent());
            for(Node group:anchorPane.getChildren()){
                if(((Label)((Group)group).getChildren().get(1)).getText().equals(message)){
                    //todo we can delete the message instead!

                    //  anchorPane.getChildren().remove(group);
                    ((Label) ((Group) group).getChildren().get(1)).setText(message.substring(0,message.indexOf(":")+1)+
                            " This message was deleted!");
                    break;
                }
            }
            deselectMessage();
        });
    }
    public void selectMessage(Label selectedMessage){
        this.selectedMessage=selectedMessage;
        DropShadow selectEffect = new DropShadow(BlurType.values()[1],
                GREEN, 10, 2.0f, 0, 0);
        selectedMessage.setEffect(selectEffect);
        if(!selectedMessage.getText().contains("This message was deleted!")) {
            if(selectedMessage.getText().startsWith(User.loggedInUser.getNickname())){
                disableEditImage(false);
            }
            else{
                disableEditImage(true);
            }
            disableDeleteImage(false);
            disablePinImage(false);
        }else{
            disableDeleteImage(true);
            disableEditImage(true);
            disablePinImage(true);
        }
    }
    public void deselectMessage(){
        if(selectedMessage!=null) {
            selectedMessage.setEffect(null);
            this.selectedMessage = null;
        }
            disableDeleteImage(true);
            disableEditImage(true);
            disablePinImage(true);
    }

    public void editMessage(MouseEvent mouseEvent) {
        message.requestFocus();
        message.setText(selectedMessage.getText().substring(selectedMessage.getText().indexOf(":")+2));
        shouldEditMessage=true;
        disablePinImage(true);
        disableDeleteImage(true);
        disableEditImage(true);

        replyMessage.setText(message.getText());
        replyMessage.setTextFill(Color.ORANGE);
        replyMessage.setBackground(new Background(new BackgroundFill(Color.CYAN,CornerRadii.EMPTY,null)));
        cancelReply.setDisable(false);
        cancelReply.setOpacity(1);
    }
    public void editMessage(String messageText,String newValue){
        Platform.runLater(()->{
            AnchorPane anchorPane=((AnchorPane)chatBox.getContent());
            for(Node group:anchorPane.getChildren()){
                if(((Label)((Group)group).getChildren().get(1)).getText().equals(messageText)){


                    ((Label) ((Group) group).getChildren().get(1)).setText(messageText.substring(0,
                            messageText.indexOf(":")+1)+newValue.substring(newValue.indexOf(":")+1));
                    break;
                }
            }
        });
    }

    public void pinMessage(MouseEvent mouseEvent) {
        try {
            dataOutputStreamForChat.writeUTF("pin message: " + selectedMessage.getText());
            dataOutputStreamForChat.flush();
        }catch (Exception e){}
    }

    public void pinMessage(String message){
        Platform.runLater(()->{
            pinnedMessage.setText("Pinned Message: "+message);
            pinnedMessage.setTextFill(Color.ORANGE);
            if(!chatPane.getChildren().contains(pinnedMessage)) {
                chatPane.getChildren().add(pinnedMessage);
                chatPane.getChildren().add(unpinImage);
            }
            deselectMessage();
        });
    }

    public void unpinMessage(){
        try {
            dataOutputStreamForChat.writeUTF("unpin message");
            dataOutputStreamForChat.flush();
        }catch (Exception e){}
    }
    public void unpinMessageForAll(){
        Platform.runLater(()->{
            chatPane.getChildren().remove(pinnedMessage);
            chatPane.getChildren().remove(unpinImage);
        });
    }
    public void disableDeleteImage(boolean disable){
        if(disable){
            deleteImage.setDisable(true);
            deleteImage.setOpacity(0.2);
        }
        else{
            deleteImage.setDisable(false);
            deleteImage.setOpacity(1);
        }
    }
    public void disableEditImage(boolean disable){
        if(disable){
            editImage.setDisable(true);
            editImage.setOpacity(0.2);
        }
        else{
           editImage.setDisable(false);
           editImage.setOpacity(1);
        }

    }
    public void disablePinImage(boolean disable){
        if(disable){
           pinImage.setDisable(true);
           pinImage.setOpacity(0.2);
        }
        else{
           pinImage.setDisable(false);
            pinImage.setOpacity(1);
        }

    }
}
