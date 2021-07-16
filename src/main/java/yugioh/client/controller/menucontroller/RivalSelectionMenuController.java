package yugioh.client.controller.menucontroller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import yugioh.client.controller.DataBaseController;
import yugioh.client.model.User;
import yugioh.client.view.NetAdapter;
import yugioh.client.view.SoundPlayable;
import yugioh.client.view.ViewInterface;
import yugioh.client.view.menus.ChatRoom;
import yugioh.client.view.menus.PopUpWindow;
import yugioh.client.view.menus.RivalSelectionMenu;
import yugioh.client.view.menus.WelcomeMenu;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.regex.Matcher;

public class RivalSelectionMenuController extends MenuController implements Initializable {

    public static RivalSelectionMenuController deckMenuController;
    private static boolean isUserFirst;

    private static User[] awaitingUsersForOneRound;
    private static User[] awaitingUsersForThreeRounds;

    public ToggleButton threeRounds;
    public ToggleButton oneRound;
    public JFXButton start;
    public MediaView rivalSelectionMenuBackground;
    public GridPane waitingUsersGridPane;

    public ScrollPane chatBox;
    public transient Thread chatThread;
    public static Scanner input = new Scanner(System.in);
    public ImageView sendImage;
    public JFXTextField message;
    private boolean isChatEnded = false;

    public RivalSelectionMenuController() {
        deckMenuController = this;
    }

    public static RivalSelectionMenuController getInstance() {//todo debug 3 rounds
        return Objects.requireNonNullElseGet(deckMenuController, RivalSelectionMenuController::new);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(new File("src\\resources\\yugioh\\Backgrounds\\main.mp4").toURI().toString()));
        mediaPlayer.play();
        mediaPlayer.setCycleCount(-1);
        rivalSelectionMenuBackground.setMediaPlayer(mediaPlayer);
        chatBoxInitialize();
        RivalSelectionMenu.setDoCancel(false);
        RivalSelectionMenu.setRival(null);
        oneRound.setSelected(false);
        threeRounds.setSelected(true);
        RivalSelectionMenu.setRounds(3);
        awaitingUsersForOneRound = DataBaseController.parseAllUsers(NetAdapter.sendRequest("get awaiting users for 1 round"));
        for (int i = 0; i < awaitingUsersForOneRound.length; i++) {
            User user = awaitingUsersForOneRound[i];
            waitingUsersGridPane.add(new Text("1 ROUND"), 0, i);
            waitingUsersGridPane.add(new Text(user.getUsername()), 1, i);
            waitingUsersGridPane.add(new Text("SCORE: " + user.getScore()), 2, i);
        }
        awaitingUsersForThreeRounds = DataBaseController.parseAllUsers(NetAdapter.sendRequest("get awaiting users for 3 rounds"));
        for (int i = 0; i < awaitingUsersForThreeRounds.length; i++) {
            User user = awaitingUsersForThreeRounds[i];
            waitingUsersGridPane.add(new Text("3 ROUNDS"), 0, i + awaitingUsersForOneRound.length);
            waitingUsersGridPane.add(new Text(user.getUsername()), 1, i + awaitingUsersForOneRound.length);
            waitingUsersGridPane.add(new Text("SCORE: " + user.getScore()), 2, i + awaitingUsersForOneRound.length);
        }
    }

    public void cancel() throws Exception {//todo handle cancel
        dataOutputStreamForChat.writeUTF(User.loggedInUser.getUsername() + " exited Chatroom");
        dataOutputStreamForChat.flush();
        SoundPlayable.playButtonSound("backButton");
        RivalSelectionMenu.setDoCancel(true);
        chatThread.stop();

        new Thread(() -> {
            while (!isChatEnded) {
            }
                try {
                    RivalSelectionMenu.getStage().close();
                } catch (Exception e) {
                }
            }).start();

    }

    public void startGame() {//todo handle cancel & back
        new Thread(() -> {
            try {
                String result = NetAdapter.dataInputStream.readUTF();
                if (result.startsWith("Error: ")) {
                    new PopUpWindow(result).start(RivalSelectionMenu.getStage());
                    return;
                }
                if (!result.startsWith("success ")) {
                    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
                        try {
                            startGame();
                        } catch (Exception ignored) {
                        }
                    }));
                    timeline.play();
                    return;
                }
                Matcher matcher = ViewInterface.getCommandMatcher(result, "success (.+)");
                User rival = DataBaseController.getUserObjectByString(matcher.group(1));
                SoundPlayable.playButtonSound("enterButton");
                RivalSelectionMenu.setRival(rival);
                RivalSelectionMenu.setDoCancel(false);
                Platform.runLater(() -> RivalSelectionMenu.getStage().close());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void clickOneRound() {
        SoundPlayable.playButtonSound("enterButton");
        threeRounds.setSelected(false);
        oneRound.setSelected(true);
        RivalSelectionMenu.setRounds(1);
    }

    public void clickThreeRounds() {
        SoundPlayable.playButtonSound("enterButton");
        oneRound.setSelected(false);
        threeRounds.setSelected(true);
        RivalSelectionMenu.setRounds(3);
    }

    public void sendGameRequest() {
        NetAdapter.justSendRequest("duel --new --rounds " + RivalSelectionMenu.getRounds());
        int tableRow = awaitingUsersForOneRound.length + awaitingUsersForThreeRounds.length;
        String rounds;
        if (RivalSelectionMenu.getRounds() == 3) {
            rounds = "3 ROUNDS";
            isUserFirst = awaitingUsersForThreeRounds.length == 0;
        } else {
            rounds = "1 ROUND";
            isUserFirst = awaitingUsersForOneRound.length == 0;
        }
        waitingUsersGridPane.add(new Text(rounds), 0, tableRow);
        waitingUsersGridPane.add(new Text(User.loggedInUser.getUsername()), 1, tableRow);
        waitingUsersGridPane.add(new Text("SCORE: " + User.loggedInUser.getScore()), 2, tableRow);
        start.setDisable(true);
        startGame();
    }

    public static boolean isIsUserFirst() {
        return isUserFirst;
    }

    public static void setIsUserFirst(boolean isUserFirst) {
        RivalSelectionMenuController.isUserFirst = isUserFirst;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////


    public void sendMessage(Event event) throws Exception {
        dataOutputStreamForChat.writeUTF("chat " + User.loggedInUser.getNickname() + ": " + message.getText());
        dataOutputStreamForChat.flush();
        message.setText("");
    }

    public void chatBoxInitialize() {
        //todo don't delete it!
/*        sendImage.setDisable(true);
        sendImage.setOpacity(0.5);
        RivalSelectionMenu.stage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
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
                if (t1.equals("")) {
                    sendImage.setDisable(true);
                    sendImage.setOpacity(0.5);
                } else {
                    sendImage.setDisable(false);
                    sendImage.setOpacity(1);
                }
            }
        });
        chatThread = new Thread(() -> {
            while (true) {
                try {
                    String inputMessage = dataInputStreamForChat.readUTF();
                    System.out.println(inputMessage);
                    if (inputMessage.equals(User.loggedInUser.getUsername() + " gomsho")) {
                        System.out.println("got it");
                        isChatEnded = true;
                        return;
                    }
                    if (!inputMessage.equals("")) {
                        Platform.runLater(() -> {
                            if (!inputMessage.startsWith(User.loggedInUser.getNickname())) {
                                AnchorPane anchorPane = (AnchorPane) chatBox.getContent();
                                double yLastMessage;
                                if (anchorPane.getChildren().size() > 0) {
                                    Label lastMessage = (Label) anchorPane.getChildren().get(anchorPane.getChildren().size() - 1);
                                    yLastMessage = lastMessage.getLayoutY();
                                } else {
                                    yLastMessage = -10;
                                }
                                Label messageLabel = new Label(inputMessage);

                                messageLabel.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, null)));
                                messageLabel.setTextFill(Color.CHARTREUSE);
                                messageLabel.setLayoutY(yLastMessage + 30);
                                messageLabel.setLayoutX(200);
                                anchorPane.getChildren().add(messageLabel);
                                chatBox.setContent(anchorPane);

                            } else {
                                AnchorPane anchorPane = (AnchorPane) chatBox.getContent();
                                double yLastMessage;
                                if (anchorPane.getChildren().size() > 0) {
                                    Label lastMessage = (Label) anchorPane.getChildren().get(anchorPane.getChildren().size() - 1);
                                    yLastMessage = lastMessage.getLayoutY();
                                } else {
                                    yLastMessage = -10;
                                }
                                Label messageLabel = new Label(inputMessage);
                                messageLabel.setTextFill(Color.RED);
                                messageLabel.setBackground(new Background(new BackgroundFill(Color.CHARTREUSE, CornerRadii.EMPTY, null)));
                                messageLabel.setLayoutY(yLastMessage + 30);
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
        chatThread.start();*/
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        dataOutputStreamForChat.writeUTF(User.loggedInUser.getUsername() + " exited Chatroom");
        dataOutputStreamForChat.flush();
        SoundPlayable.playButtonSound("backButton");
        chatThread.stop();
        System.out.println("send it");
        new Thread(() -> {
            while (!isChatEnded) {
            }
            Platform.runLater(() -> {
                try {
                    System.out.println("should execute");
                    mainMenu.execute();
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

}


