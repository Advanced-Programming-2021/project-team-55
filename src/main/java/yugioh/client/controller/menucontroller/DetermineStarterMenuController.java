package yugioh.client.controller.menucontroller;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import yugioh.client.controller.gamephasescontrollers.GameController;
import yugioh.client.model.Player;
import yugioh.client.view.NetAdapter;
import yugioh.client.view.gamephases.Duel;
import yugioh.client.view.menus.*;
import yugioh.server.view.ViewInterface;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;

import static yugioh.client.view.SoundPlayable.playButtonSound;

public class DetermineStarterMenuController implements Initializable {

    private static GameController gameController;
    public JFXButton head;
    public JFXButton tale;
    public Label firstPlayerName;
    public Label yesNoQuestion;
    public JFXButton noButton;
    public JFXButton yesButton;
    public MediaView coinToss;

    public DetermineStarterMenuController() {
        DetermineStarterMenuController.gameController = Duel.getGameController();
    }

    public static void setGameController(GameController gameController) {
        DetermineStarterMenuController.gameController = gameController;
    }

    private void assignTurn(String choice) {
        String currentPlayerName = gameController.getGame().getFirstPlayer().getUser().getNickname();
        String opponentPlayerName = gameController.getGame().getSecondPlayer().getUser().getNickname();

        int randomNumber = gameController.tossCoin();
        switch (randomNumber) {
            case 1:
                Platform.runLater(() -> Toast.makeText(DetermineStarterMenu.getStage(), "HEAD!"));
                break;
            case 2:
                Platform.runLater(() -> Toast.makeText(DetermineStarterMenu.getStage(), "TALE!"));
                break;
        }
        if (Integer.parseInt(choice) == randomNumber) {
            yesNoQuestion.setText(currentPlayerName + " do you want to be the first player?");
            noButton.setOpacity(1);
            yesButton.setOpacity(1);
            disableIfIsRival(gameController.getGame().getFirstPlayer());
        } else {
            yesNoQuestion.setText(opponentPlayerName + " do you want to be the first player?");
            noButton.setOpacity(1);
            yesButton.setOpacity(1);
            disableIfIsRival(gameController.getGame().getSecondPlayer());
        }
    }

    private void disableIfIsRival(Player secondPlayer) {
        if (RivalSelectionMenu.isRival(secondPlayer)) {
            yesButton.setDisable(true);
            noButton.setDisable(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String currentPlayerName = gameController.getGame().getFirstPlayer().getUser().getNickname();
        firstPlayerName.setText(currentPlayerName + " choose your Coin side:");
        noButton.setOpacity(0);
        yesButton.setOpacity(0);
        playSound();
        if (RivalSelectionMenu.isRival(gameController.getGame().getFirstPlayer())) {
            head.setDisable(true);
            tale.setDisable(true);
        }
        startSyncingWithRival();
    }

    private void startSyncingWithRival() {
        Thread listeningCommandThread = new Thread(() -> {
            try {
                while (true) {
                    String command = NetAdapter.dataInputStream.readUTF();
                    if (command.startsWith("forward: ")) {
                        Matcher matcher = ViewInterface.getCommandMatcher(command, "forward: (.+)");
                        command = matcher.group(1);
                        switch (command) {
                            case "yes selected":
                                Platform.runLater(this::handleYesButton);
                                break;
                            case "no selected":
                                Platform.runLater(this::handleNoButton);
                                break;
                            case "head selected":
                                Platform.runLater(this::handleHead);
                                break;
                            case "tale selected":
                                Platform.runLater(this::handleTale);
                                break;
                            default:
                                String finalCommand = command;
                                Platform.runLater(() -> Duel.handleCommand(finalCommand));
                        }
                    } else System.out.println("not handled: " + command);
                }
            } catch (IOException ignored) {
            }
        });
        listeningCommandThread.start();
    }

    private void playSound() {
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(new File("src\\resources\\yugioh\\Backgrounds\\coinToss.mp4").toURI().toString()));
        mediaPlayer.play();
        mediaPlayer.setCycleCount(1);
        mediaPlayer.setMute(true);
        coinToss.setMediaPlayer(mediaPlayer);
    }

    public void back() throws Exception {
        playButtonSound("backButton");
//        new DuelMenu().execute();
        DetermineStarterMenu.getStage().close();
    }

    public void headClicked() {
        NetAdapter.sendForwardRequest("head selected");
        handleHead();
    }

    private void handleHead() {
        playButtonSound("enterButton");
        head.setDisable(true);
        tale.setDisable(true);
        assignTurn("1");
    }

    public void taleClicked() {
        NetAdapter.sendForwardRequest("tale selected");
        handleTale();
    }

    private void handleTale() {
        playButtonSound("enterButton");
        head.setDisable(true);
        tale.setDisable(true);
        assignTurn("2");
    }

    public void noClicked() {
        NetAdapter.sendForwardRequest("no selected");
        handleNoButton();
    }

    private void handleNoButton() {
        playButtonSound("backButton");
        String currentPlayerName = gameController.getGame().getFirstPlayer().getUser().getNickname();
        String opponentPlayerName = gameController.getGame().getSecondPlayer().getUser().getNickname();
        if (yesNoQuestion.getText().startsWith(currentPlayerName)) {
            gameController.setCurrentTurnPlayer(gameController.getGame().getSecondPlayer());
            gameController.setCurrentTurnOpponentPlayer(gameController.getGame().getFirstPlayer());
        } else if (yesNoQuestion.getText().startsWith(opponentPlayerName)) {
            gameController.setCurrentTurnPlayer(gameController.getGame().getFirstPlayer());
            gameController.setCurrentTurnOpponentPlayer(gameController.getGame().getSecondPlayer());
        }
        DetermineStarterMenu.getStage().close();
        playButtonSound("forHonor");
    }

    public void yesClicked() {
        NetAdapter.sendForwardRequest("yes selected");
        handleYesButton();
    }

    private void handleYesButton() {
        playButtonSound("enterButton");
        String currentPlayerName = gameController.getGame().getFirstPlayer().getUser().getNickname();
        String opponentPlayerName = gameController.getGame().getSecondPlayer().getUser().getNickname();
        if (yesNoQuestion.getText().startsWith(currentPlayerName)) {
            gameController.setCurrentTurnPlayer(gameController.getGame().getFirstPlayer());
            gameController.setCurrentTurnOpponentPlayer(gameController.getGame().getSecondPlayer());
        } else if (yesNoQuestion.getText().startsWith(opponentPlayerName)) {
            gameController.setCurrentTurnPlayer(gameController.getGame().getSecondPlayer());
            gameController.setCurrentTurnOpponentPlayer(gameController.getGame().getFirstPlayer());
        }
        DetermineStarterMenu.getStage().close();
        playButtonSound("forHonor");
    }

}
